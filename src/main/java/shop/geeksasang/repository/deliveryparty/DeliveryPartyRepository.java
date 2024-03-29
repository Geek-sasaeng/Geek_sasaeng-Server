package shop.geeksasang.repository.deliveryparty;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.deliveryparty.DeliveryParty;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface DeliveryPartyRepository extends JpaRepository<DeliveryParty,Integer> {

    @Query("select dp from DeliveryParty dp where dp.id = :deliveryPartyId and dp.status = 'ACTIVE' and dp.matchingStatus = 'ONGOING'")
    Optional<DeliveryParty> findDeliveryPartyById(int deliveryPartyId);

    @Query("select dp from DeliveryParty dp where dp.id = :partyId and dp.status = 'ACTIVE' and dp.matchingStatus = 'ONGOING' and dp.chief.id = :userId")
    Optional<DeliveryParty> findDeliveryPartyByIdAndUserIdAndMatchingStatus(int partyId, int userId);

    @Query("select dp from DeliveryParty dp where dp.id = :deliveryPartyId and dp.status='ACTIVE' and dp.matchingStatus = 'FINISH'")
    Optional<DeliveryParty> findDeliveryPartyByIdAndMatchingStatus(int deliveryPartyId);

    // 배달파티 상세조회: 현재시각이 주문시각 전인 것
    @Query("select dp from DeliveryParty dp where dp.id = :deliveryPartyId and dp.status = 'ACTIVE' and dp.matchingStatus = 'ONGOING' and dp.orderTime >= :currentTime")
    Optional<DeliveryParty> findDeliveryPartyByIdBeforeOrderTime(int deliveryPartyId, @Param("currentTime") LocalDateTime currentTime);

    @Query("select dp from DeliveryParty dp where dp.id = :deliveryPartyId and dp.status = 'ACTIVE' and dp.chief.id = :userId")
    Optional<DeliveryParty> findDeliveryPartyByPartyId(int deliveryPartyId, int userId);

    @Query("select dp from DeliveryParty dp where dp.id = :deliveryPartyId and dp.status = 'ACTIVE'")
    Optional<DeliveryParty> findDeliveryPartyByIdAndStatus(int deliveryPartyId);

    //배달파티 조회: 검색어로 조회
    @Query("select dp from DeliveryParty dp " +
            "where dp.dormitory.id = :dormitoryId and (dp.title LIKE CONCAT('%',:keyword,'%') or dp.foodCategory.title = :keyword) and dp.matchingStatus = 'ONGOING' and dp.status = 'ACTIVE' and dp.orderTime >= :currentTime")
    Slice<DeliveryParty> findDeliveryPartiesByKeyword(int dormitoryId, @Param("keyword") String keyword, Pageable pageable, @Param("currentTime") LocalDateTime currentTime);

}
