package shop.geeksasang.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.config.domain.OrderTimeCategoryType;
import shop.geeksasang.domain.DeliveryParty;

import javax.persistence.TypedQuery;

@Repository
public interface DeliveryPartyRepository extends JpaRepository<DeliveryParty,Integer> {

    @Query("select dp from DeliveryParty dp where dp.domitory.id = :domitoryId")
    Slice<DeliveryParty> findDeliveryPartiesByDomitoryId(int domitoryId, Pageable pageable);

    @Query("select dp from DeliveryParty dp where dp.domitory.id = :domitoryId and dp.maxMatching <= :maxMatching")
    Slice<DeliveryParty> findDeliveryPartiesByMaxMatching(int domitoryId, int maxMatching, Pageable pageable);

    @Query("select dp from DeliveryParty dp where dp.domitory.id = :domitoryId and dp.orderTimeCategory = :orderTimeCategory")
    Slice<DeliveryParty> findDeliveryPartiesByOrderTime(int domitoryId, @Param("orderTimeCategory") OrderTimeCategoryType orderTimeCategory, Pageable pageable);


}
