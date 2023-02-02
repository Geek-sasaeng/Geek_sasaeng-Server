package shop.geeksasang.repository.deliveryparty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import shop.geeksasang.domain.deliveryparty.DeliveryPartyMember;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryPartyMemberRepository extends JpaRepository<DeliveryPartyMember, Integer> {

    @Query("select dpm from DeliveryPartyMember dpm where dpm.participant.id = :memberId and dpm.party.id = :partyId and dpm.participant.status ='ACTIVE'")
    Optional<DeliveryPartyMember> findDeliveryPartyMemberByMemberIdAndDeliveryPartyIdNotUseStatus(int memberId, int partyId);

    @Query("select dpm from DeliveryPartyMember dpm where dpm.status = 'ACTIVE' and dpm.participant.id = :memberId and dpm.party.id = :partyId and dpm.participant.status ='ACTIVE'")
    Optional<DeliveryPartyMember> findDeliveryPartyMemberByMemberIdAndDeliveryPartyId(int memberId, int partyId);

    @Query("select dpm from DeliveryPartyMember dpm join fetch dpm.party where dpm.participant.id = :userId and dpm.party.status = 'ACTIVE'and dpm.status = 'ACTIVE'")
    List<DeliveryPartyMember> findByPartiesByDeliveryPartyMemberId(int userId);

    @Query("select dpm from DeliveryPartyMember dpm where dpm.party.id= :id and dpm.participant.nickName = :nickName and dpm.status = 'ACTIVE'")
    Optional<DeliveryPartyMember> findByDeliveryPartyMemberByIdAndNickName(int id, String nickName);
}
