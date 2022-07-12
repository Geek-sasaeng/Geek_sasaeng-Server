package shop.geeksasang.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.DeliveryParty;

@Repository
public interface DeliveryPartyRepository extends JpaRepository<DeliveryParty,Integer> {

    @Query("select dp from DeliveryParty dp where dp.domitory.id = :domitoryId")
    Slice<DeliveryParty> findDeliveryPartiesByDomitoryId(int domitoryId, Pageable pageable);

}
