package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.ReportCategory;
import shop.geeksasang.domain.report.MemberReport;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.dto.report.PostMemberReportRegisterReq;
import shop.geeksasang.repository.MemberReportRepository;
import shop.geeksasang.repository.MemberRepository;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberReportService {

    private final MemberReportRepository memberReportRepository;
    private final ReportCategoryRepository reportCategoryRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = false)
    public void registerMemberReport(PostMemberReportRegisterReq dto, JwtInfo jwtInfo){
        //멤버를 가져온다.
        int memberId = jwtInfo.getUserId();
        Member member = memberRepository.findMemberById(memberId).orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        //하루 신고 횟수 확인
        if(member.checkPerDayReportCount()){
            throw new BaseException(INVALID_REPORT_COUNT);
        }

        //신고 당한 멤버가 우리 애플리케이션 이미 존재하는지 체크
        Member reportedMember = memberRepository.findMemberById(dto.getReportedMemberId())
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        //중복 신고인지 검사한다.
        if(member.containReportedMemberRecord(reportedMember)){
            throw new BaseException(EXIST_REPORT_RECORD);
        }

        //카테고리 가져온다.
        ReportCategory reportCategory = reportCategoryRepository.findById(dto.getReportCategoryId())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_REPORT_CATEGORY));

        //신고 생성
        MemberReport report = dto.toEntity(member, reportedMember, dto, reportCategory);
        memberReportRepository.save(report);

        //신고 기록 추가 & 하루 신고 제한 횟수 추가
        member.addMemberReportRecord(reportedMember);

        //신고당한 횟수추가 그리고 3회 이상이면 정지
        reportedMember.addReportedCountAndCheckReportedCount();
    }
}