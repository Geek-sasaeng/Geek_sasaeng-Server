package shop.geeksasang.utils.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shop.geeksasang.domain.VerificationCount;
import shop.geeksasang.repository.VerificationCountRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VerificationScheduler {
    final VerificationCountRepository verificationCountRepository;

    // 핸드폰, 이메일 인증 횟수 초과 매일 자정에 초기화
    @Scheduled(cron = "0 0 0 * * *")
    public void resetVerificationCount(){
        List<VerificationCount> verificationCountList = verificationCountRepository.findAll();
        for(VerificationCount verificationCount: verificationCountList){
            verificationCount.resetVerificationCount();
        }
    }
}
