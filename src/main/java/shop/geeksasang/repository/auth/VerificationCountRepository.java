package shop.geeksasang.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.auth.VerificationCount;

import java.util.Optional;

@Repository
public interface VerificationCountRepository extends JpaRepository<VerificationCount, Integer> {

    Optional<VerificationCount> findEmailVerificationCountByUUID(String uuid);

    Optional<VerificationCount> findVerificationCountByUUID(String uuid);

    @Modifying
    @Query("update VerificationCount v set v.emailVerificationCount = 0")
    int bulkEmailVerificationInit();

    @Modifying
    @Query("update VerificationCount v set v.smsVerificationCount = 0")
    int bulkSmsVerificationCountInit();

}
