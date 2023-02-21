package shop.geeksasang.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.geeksasang.domain.auth.PhoneNumber;

import java.util.Optional;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer> {

    @Query("select p from PhoneNumber p where p.number = :number and p.status = 'ACTIVE'")
    Optional<PhoneNumber> findPhoneNumberByNumber(String number);
}
