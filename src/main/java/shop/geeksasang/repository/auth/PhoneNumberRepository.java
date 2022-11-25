package shop.geeksasang.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.geeksasang.domain.auth.PhoneNumber;

import java.util.Optional;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer> {
    Optional<PhoneNumber> findPhoneNumberByNumber(String number);
}
