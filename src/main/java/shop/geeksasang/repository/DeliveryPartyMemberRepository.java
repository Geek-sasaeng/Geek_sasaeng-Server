package shop.geeksasang.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.DeliveryPartyMember;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryPartyMemberRepository extends JpaRepository<DeliveryPartyMember, Integer> {

    @Query("select dpm from DeliveryPartyMember dpm where dpm.status = 'ACTIVE' and dpm.party.id = :deliveryPartyId")
    List<DeliveryPartyMember> findDeliveryPartyMembersByPartyId(int deliveryPartyId);

    @Query("select dpm from DeliveryPartyMember dpm where dpm.status = 'ACTIVE' and dpm.participant.id = :memberId")
    Optional<DeliveryPartyMember> findDeliveryPartyMemberById(int memberId);
}
