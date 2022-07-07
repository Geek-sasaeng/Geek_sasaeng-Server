package shop.geeksasang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.VerificationCount;

import java.util.Optional;

@Repository
public interface SmsVerificationCountRepository extends JpaRepository<VerificationCount, Integer> {

    Optional<VerificationCount> findSmsVerificationCountByClientIp(String clientIp);
}
