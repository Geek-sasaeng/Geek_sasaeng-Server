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

import static shop.geeksasang.config.exception.response.BaseResponseStatus.NOT_EXIST_USER;

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
        if(member.checkPerDayReportCopunt()){
            throw new RuntimeException("하루 3번만 신고 가능");
        }

        //신고 당한 멤버가 이미 존재하는지 체크
        Member reportedMember = memberRepository.findMemberById(dto.getReportedMemberId())
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        //문제 없으면 멤버 id를 가져와서 신고를 넣고
        if(member.containReportedMemberRecord(reportedMember)){
            throw new RuntimeException("이미 신고함");
        }

        //신고 기록에 추가
        member.addMemberReportRecord(reportedMember);

        //카테고리 가져온다.
        ReportCategory reportCategory = reportCategoryRepository.findById(dto.getReportCategoryId())
                .orElseThrow(() -> new RuntimeException("카테고리가 없음"));

        //신고 생성
        MemberReport report = dto.toEntity(member, reportedMember, dto, reportCategory);
        memberReportRepository.save(report);

        //멤버 하루 총 신고 횟수 추가
        member.addOneDayReportCount();

        //신고당한 횟수추가 그리고 3회 이상이면 정지
        member.addReportedCountAndCheckReportedCount();
    }
}
