package shop.geeksasang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.DeliveryPartyMenu;

@Repository
public interface DeliveryPartyMenuRepository extends JpaRepository<DeliveryPartyMenu, Integer> {
}
