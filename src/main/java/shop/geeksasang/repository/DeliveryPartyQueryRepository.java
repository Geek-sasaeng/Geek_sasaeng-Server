package shop.geeksasang.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import shop.geeksasang.config.type.OrderTimeCategoryType;
import shop.geeksasang.domain.DeliveryParty;
import javax.persistence.EntityManager;
import java.util.List;
import static shop.geeksasang.domain.QDeliveryParty.*;


@Repository
public class DeliveryPartyQueryRepository {

    private final JPAQueryFactory query;

    @Autowired
    public DeliveryPartyQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<DeliveryParty> findDeliveryPartiesByConditions(int dormitoryId, OrderTimeCategoryType orderTimeCategory, Integer maxMatching, Pageable pageable) {
        return query.select(deliveryParty)
                .from(deliveryParty)
                .where(deliveryParty.dormitory.id.eq(dormitoryId),
                        orderTimeCategory == null ? null : deliveryParty.orderTimeCategory.eq(orderTimeCategory), //eq는 null 들어가면 문제 발생
                        deliveryParty.maxMatching.between(0, maxMatching)) //null 들어가면 알아서 조건이 반영되지 않는다.
                .orderBy(deliveryParty.orderTime.asc(), deliveryParty.id.asc())
                .offset(pageable.getOffset())
                .limit(10)
                .fetch();
    }
}
