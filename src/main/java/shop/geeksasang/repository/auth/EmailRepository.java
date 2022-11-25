package shop.geeksasang.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.geeksasang.domain.auth.Email;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Integer> {
    Optional<Email> findEmailByAddress(String name);
}
