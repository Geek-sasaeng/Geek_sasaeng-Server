package shop.geeksasang.utils.scheduler.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.domain.Member;
import shop.geeksasang.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportSchedulerService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = false)
    public void resetPerDayMemberReportingCount(){
        memberRepository.bulkDayReportingCountInit();
    }
}
