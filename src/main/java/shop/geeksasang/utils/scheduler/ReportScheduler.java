package shop.geeksasang.utils.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shop.geeksasang.domain.Member;
import shop.geeksasang.repository.MemberRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportScheduler {

    private final MemberRepository memberRepository;

    //하루 최대 신고 회수 3회 초기화
    @Scheduled(cron = "0 0 0 * * *")
    public void resetPerDayMemberReportingCount(){
        List<Member> memberList = memberRepository.findAll();
        for (Member member : memberList) {
            member.resetPerDayReportingCount();
        }
    }
}
