package shop.geeksasang.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import shop.geeksasang.config.status.BaseStatus;
import shop.geeksasang.config.type.OrderTimeCategoryType;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.dto.deliveryParty.get.DeliveryPartiesVo;
import shop.geeksasang.dto.deliveryParty.get.GetDeliveryPartiesRes;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static shop.geeksasang.domain.QDeliveryParty.*;


@Repository
public class DeliveryPartyQueryRepository {

    private final JPAQueryFactory query;

    @Autowired
    public DeliveryPartyQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public GetDeliveryPartiesRes findDeliveryPartiesByConditions(int dormitoryId, OrderTimeCategoryType orderTimeCategory, Integer maxMatching, Pageable pageable) {
        List<DeliveryParty> DeliveryPartyList = query.select(deliveryParty)
                .from(deliveryParty)
                .where(deliveryParty.dormitory.id.eq(dormitoryId),
                        orderTimeCategory == null ? null : deliveryParty.orderTimeCategory.eq(orderTimeCategory), //eq는 null 들어가면 문제 발생
                        deliveryParty.maxMatching.between(0, maxMatching), //null 들어가면 알아서 조건이 반영되지 않는다.
                        deliveryParty.status.eq(BaseStatus.ACTIVE))
                .orderBy(deliveryParty.orderTime.asc(), deliveryParty.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1) // 페이징을 위해 11개를 가져온다.
                .fetch();

        boolean isFinalPage = true; //마지막 페이지인가

        if(DeliveryPartyList.size() == pageable.getPageSize() + 1){ //11이면 다음 페이지가 있다
            DeliveryPartyList.remove(pageable.getPageSize()); //10개만 리턴하니까 지워버림. 콜렉션은 배열과 마찬가지로 0부터 시작.
            isFinalPage = false;
        }

        List<DeliveryPartiesVo> deliveryPartiesVoList = DeliveryPartyList.stream()
                .map(deliveryParty -> DeliveryPartiesVo.toDto(deliveryParty))
                .collect(Collectors.toList());

        return new GetDeliveryPartiesRes(isFinalPage, deliveryPartiesVoList);
    }
}

//INACTIVE, REPORTED 조건 추가하기