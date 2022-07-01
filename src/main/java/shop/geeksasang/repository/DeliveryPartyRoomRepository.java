package shop.geeksasang.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.DeliveryPartyRoom;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.CreateMemberReq;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class DeliveryPartyRoomRepository {
    private final EntityManager em;

    public void save(DeliveryPartyRoom deliveryPartyRoom) {
        em.persist(deliveryPartyRoom);
    }
}
