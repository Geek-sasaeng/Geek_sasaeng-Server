package shop.geeksasang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.geeksasang.domain.Email;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Integer> {
    Optional<Email> findEmailByName(String name);
}
