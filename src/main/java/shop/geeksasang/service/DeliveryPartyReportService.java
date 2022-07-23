package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.ReportCategory;
import shop.geeksasang.domain.report.DeliveryPartyReport;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.dto.report.PostDeliveryPartyReportRegisterReq;
import shop.geeksasang.repository.DeliveryPartyReportRepository;
import shop.geeksasang.repository.DeliveryPartyRepository;
import shop.geeksasang.repository.MemberRepository;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;


@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryPartyReportService {

    private final MemberRepository memberRepository;
    private final DeliveryPartyRepository deliveryPartyRepository;
    private final ReportCategoryRepository reportCategoryRepository;
    private final DeliveryPartyReportRepository deliveryPartyReportRepository;

    @Transactional(readOnly = false)
    public void registerDeliveryPartyReport(PostDeliveryPartyReportRegisterReq dto, JwtInfo jwtInfo){
        //멤버를 가져온다.
        int memberId = jwtInfo.getUserId();
        Member member = memberRepository.findMemberById(memberId).orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        //하루 총 신고 횟수 확인
        if(member.checkPerDayReportCount()){
            throw new BaseException(INVALID_REPORT_COUNT);
        }

        //배달파티 id를 가져옴.
        DeliveryParty deliveryParty = deliveryPartyRepository.findById(dto.getReportedDeliveryPartyId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTY));

        //멤버가 이미 신고했는지 체크 문제가 없다면 레코드에 추가
        if(member.containReportedDeliveryPartyRecord(deliveryParty)){
            throw new BaseException(EXIST_REPORT_RECORD);
        }



        //카테고리 가져온다.
        ReportCategory reportCategory = reportCategoryRepository.findById(dto.getReportCategoryId())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_REPORT_CATEGORY));

        //신고 생성
        DeliveryPartyReport report = dto.toEntity(member, deliveryParty, reportCategory, dto);
        deliveryPartyReportRepository.save(report);

        //멤버 하루 총 신고 횟수 추가 & 배달 파티 신고 기록 남기기
        member.addDeliveryPartyReportRecord(deliveryParty);

        //횟수 증가와 3번 이상인지 체크
        deliveryParty.addReportedCountAndCheckReportedCount();
    }
}

//차단은 따로 구현해야함 차단 리스트 테이블이 필요할 듯