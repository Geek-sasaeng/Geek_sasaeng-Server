package shop.geeksasang.utils.scheduler.verification;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VerificationScheduler {

    private final VerificationSchedulerService verificationSchedulerService;

    // 핸드폰, 이메일 인증 횟수 초과 매일 자정에 초기화
    @Scheduled(cron = "0 0 0 * * *")
    public void resetVerificationCount(){
        verificationSchedulerService.resetVerificationCount();
    }
}
