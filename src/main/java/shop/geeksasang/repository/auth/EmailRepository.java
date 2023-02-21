package shop.geeksasang.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.geeksasang.domain.auth.Email;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Integer> {

    @Query("select e from Email e where e.address = :emailAddress and e.status = 'ACTIVE'")
    Optional<Email> findEmailByAddressAndACTIVE(String emailAddress);
}
