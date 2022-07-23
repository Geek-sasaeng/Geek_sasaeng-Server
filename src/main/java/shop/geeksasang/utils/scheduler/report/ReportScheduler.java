package shop.geeksasang.utils.scheduler.report;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReportScheduler {

    private final ReportSchedulerService reportSchedulerService;

    //하루 최대 신고 회수 3회 초기화
    @Scheduled(cron = "0 0 0 * * *")
    public void resetPerDayMemberReportingCount(){
        reportSchedulerService.resetPerDayMemberReportingCount();
    }
}

//한달 계산은 어케하지?