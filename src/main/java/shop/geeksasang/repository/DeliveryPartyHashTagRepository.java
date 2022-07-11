package shop.geeksasang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.DeliveryPartyHashTag;

@Repository
public interface DeliveryPartyHashTagRepository extends JpaRepository<DeliveryPartyHashTag,Integer> {
}
