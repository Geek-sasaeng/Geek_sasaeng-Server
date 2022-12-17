package shop.geeksasang.utils.scheduler.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.repository.member.MemberRepository;

@Service
@RequiredArgsConstructor
public class ReportSchedulerService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = false)
    public void resetPerDayMemberReportingCount(){
        memberRepository.bulkDayReportingCountInit();
    }
}
