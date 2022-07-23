package shop.geeksasang.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.config.type.OrderTimeCategoryType;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.Email;
import shop.geeksasang.domain.Member;

import java.util.Optional;


@Repository
public interface DeliveryPartyRepository extends JpaRepository<DeliveryParty,Integer> {

    @Query("select dp from DeliveryParty dp where dp.dormitory.id = :dormitoryId")
    Slice<DeliveryParty> findDeliveryPartiesByDormitoryId(int dormitoryId, Pageable pageable);

    @Query("select dp from DeliveryParty dp where dp.dormitory.id = :dormitoryId and dp.maxMatching <= :maxMatching and dp.matchingStatus = 'ONGOING' and dp.status = 'ACTIVE'")
    Slice<DeliveryParty> findDeliveryPartiesByMaxMatching(int dormitoryId, int maxMatching, Pageable pageable);

    @Query("select dp from DeliveryParty dp where dp.dormitory.id = :dormitoryId and dp.orderTimeCategory = :orderTimeCategory and dp.matchingStatus = 'ONGOING' and dp.status = 'ACTIVE'")
    Slice<DeliveryParty> findDeliveryPartiesByOrderTime(int dormitoryId, @Param("orderTimeCategory") OrderTimeCategoryType orderTimeCategory, Pageable pageable);

    @Query("select dp from DeliveryParty dp where dp.id = :deliveryPartyId and dp.status = 'ACTIVE' and dp.matchingStatus = 'ONGOING'")
    Optional<DeliveryParty> findDeliveryPartyById(int deliveryPartyId);

    @Query("select dp from DeliveryParty dp where dp.id = :deliveryPartyId and dp.status = 'ACTIVE' and dp.chief.id = :userId")
    Optional<DeliveryParty> findDeliveryPartyByPartyId(int deliveryPartyId, int userId);

    @Query("select dp from DeliveryParty dp where dp.id = :deliveryPartyId and dp.status = 'ACTIVE'")
    Optional<DeliveryParty> findDeliveryPartyByIdAndStatus(int deliveryPartyId);

    //배달파티 조회: 검색어로 조회
    @Query("select dp from DeliveryParty dp " +
            "where dp.dormitory.id = :dormitoryId and (dp.title LIKE CONCAT('%',:keyword,'%') or dp.foodCategory.title = :keyword) and dp.matchingStatus = 'ONGOING' and dp.status = 'ACTIVE'")
    Slice<DeliveryParty> findDeliveryPartiesByKeyword(int dormitoryId, @Param("keyword") String keyword, Pageable pageable);


}
