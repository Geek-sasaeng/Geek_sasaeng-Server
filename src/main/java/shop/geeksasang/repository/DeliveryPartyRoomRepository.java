package shop.geeksasang.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shop.geeksasang.domain.DeliveryPartyRoom;

public interface DeliveryPartyRoomRepository extends JpaRepository<DeliveryPartyRoom, Integer> {
}
