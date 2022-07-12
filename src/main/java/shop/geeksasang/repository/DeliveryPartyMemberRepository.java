package shop.geeksasang.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shop.geeksasang.domain.DeliveryPartyMember;

public interface DeliveryPartyMemberRepository extends JpaRepository<DeliveryPartyMember, Integer> {
}
