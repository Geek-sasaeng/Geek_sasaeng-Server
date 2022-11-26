package shop.geeksasang.repository.deliveryparty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.deliveryparty.DeliveryPartyMenu;

@Repository
public interface DeliveryPartyMenuRepository extends JpaRepository<DeliveryPartyMenu, Integer> {
}
