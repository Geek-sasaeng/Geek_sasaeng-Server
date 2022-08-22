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

    @Query("select dpm from DeliveryPartyMember dpm where dpm.status = 'ACTIVE' and dpm.participant.id = :memberId and dpm.party.id = :partyId")
    Optional<DeliveryPartyMember> findDeliveryPartyMemberByMemberIdAndDeliveryPartyId(int memberId, int partyId);

    @Query("select dpm from DeliveryPartyMember dpm join fetch dpm.party where dpm.participant.id = :userId and dpm.party.status = 'ACTIVE' ")
    List<DeliveryPartyMember> findByPartiesByDeliveryPartyMemberId(int userId);

    @Query("select dpm from DeliveryPartyMember dpm where dpm.party.uuid = :uuid and dpm.participant.nickName = :nickName and dpm.status = 'ACTIVE'")
    Optional<DeliveryPartyMember> tempFindByDeliveryPartyMemberByUuidAndNickName(String uuid, String nickName);
}
