package shop.geeksasang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.SmsVerificationCount;

import java.util.Optional;

@Repository
public interface SmsVerificationCountRepository extends JpaRepository<SmsVerificationCount, Integer> {

    Optional<SmsVerificationCount> findSmsVerificationCountByClientIp(String clientIp);
}
