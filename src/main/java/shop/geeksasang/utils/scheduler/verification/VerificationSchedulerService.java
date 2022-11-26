package shop.geeksasang.utils.scheduler.verification;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.geeksasang.repository.auth.VerificationCountRepository;

@Service
@RequiredArgsConstructor
public class VerificationSchedulerService {

    private final VerificationCountRepository verificationCountRepository;

    @Transactional(readOnly = false)
    public void resetVerificationCount(){
        verificationCountRepository.bulkSmsVerificationCountInit();
        verificationCountRepository.bulkSmsVerificationCountInit();
    }
}
