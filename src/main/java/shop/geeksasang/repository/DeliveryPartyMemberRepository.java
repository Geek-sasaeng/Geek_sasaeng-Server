package shop.geeksasang.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.DeliveryPartyMember;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryPartyMemberRepository extends JpaRepository<DeliveryPartyMember, Integer> {

    @Query("select dpm from DeliveryPartyMember dpm where dpm.status = 'ACTIVE' and dpm.party.id = :deliveryPartyMemberId")
    List<DeliveryPartyMember> findDeliveryPartyMembersById(int deliveryPartyMemberId);
}
