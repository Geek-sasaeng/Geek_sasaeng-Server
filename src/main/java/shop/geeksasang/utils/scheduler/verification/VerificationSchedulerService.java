package shop.geeksasang.utils.scheduler.verification;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.geeksasang.domain.VerificationCount;
import shop.geeksasang.repository.VerificationCountRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VerificationSchedulerService {

    private final VerificationCountRepository verificationCountRepository;

    @Transactional(readOnly = false)
    public void resetVerificationCount(){
        List<VerificationCount> verificationCountList = verificationCountRepository.findAll();
        for(VerificationCount verificationCount: verificationCountList){
            verificationCount.resetVerificationCount();
        }

    }

}
