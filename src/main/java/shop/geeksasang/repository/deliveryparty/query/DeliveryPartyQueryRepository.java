package shop.geeksasang.repository.deliveryparty.query;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import shop.geeksasang.config.status.BaseStatus;
import shop.geeksasang.config.status.MatchingStatus;
import shop.geeksasang.config.status.OrderStatus;
import shop.geeksasang.config.type.OrderTimeCategoryType;
import shop.geeksasang.domain.deliveryparty.DeliveryParty;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.dto.deliveryParty.get.GetEndedDeliveryPartiesRes;
import shop.geeksasang.dto.deliveryParty.get.vo.DeliveryPartiesVo;
import shop.geeksasang.dto.deliveryParty.get.GetDeliveryPartiesRes;
import shop.geeksasang.dto.deliveryParty.get.vo.EndedDeliveryPartiesVo;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static shop.geeksasang.domain.deliveryparty.QDeliveryParty.*;
import static shop.geeksasang.domain.deliveryparty.QDeliveryPartyMember.*;


@Repository
public class DeliveryPartyQueryRepository {

    private final JPAQueryFactory query;

    @Autowired
    public DeliveryPartyQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public GetDeliveryPartiesRes findDeliveryPartiesByConditions(int dormitoryId, OrderTimeCategoryType orderTimeCategory, Integer maxMatching, Pageable pageable, List<Member> blockList) {
        List<DeliveryParty> DeliveryPartyList = query.select(deliveryParty)
                .from(deliveryParty)
                .where(deliveryParty.dormitory.id.eq(dormitoryId),
                        orderTimeCategory == null ? null : deliveryParty.orderTimeCategory.eq(orderTimeCategory), //eq는 null 들어가면 문제 발생
                        deliveryParty.maxMatching.between(0, maxMatching), //null 들어가면 알아서 조건이 반영되지 않는다.
                        deliveryParty.status.eq(BaseStatus.ACTIVE),
                        deliveryParty.matchingStatus.eq(MatchingStatus.ONGOING),
                        deliveryParty.orderTime.after(LocalDateTime.now()),
                        deliveryParty.chief.notIn(blockList)
                )
                .orderBy(deliveryParty.orderTime.asc(), deliveryParty.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1) // 페이징을 위해 11개를 가져온다.
                .fetch();

        JPAQuery<DeliveryParty> from = query.select(deliveryParty)
                .from(deliveryParty).where();

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

    //배달파티 조회: 검색어로 조회 필터 추가
    public GetDeliveryPartiesRes getDeliveryPartiesByKeyword2(int dormitoryId, OrderTimeCategoryType orderTimeCategory, Integer maxMatching, String keyword, Pageable pageable, List<Member> blockList) {
        List<DeliveryParty> DeliveryPartyList = query.select(deliveryParty)
                .from(deliveryParty)
                .where(deliveryParty.dormitory.id.eq(dormitoryId),
                        orderTimeCategory == null ? null : deliveryParty.orderTimeCategory.eq(orderTimeCategory), //eq는 null 들어가면 문제 발생
                        deliveryParty.maxMatching.between(0, maxMatching), //null 들어가면 알아서 조건이 반영되지 않는다.
                        deliveryParty.status.eq(BaseStatus.ACTIVE),
                        deliveryParty.matchingStatus.eq(MatchingStatus.ONGOING),
                        deliveryParty.orderTime.after(LocalDateTime.now()),
                        deliveryParty.title.like('%'+keyword+'%').or(deliveryParty.foodCategory.title.eq(keyword)),
                        deliveryParty.chief.notIn(blockList)
                )
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

    public List<DeliveryParty> findRecentOngoingDeliveryParty (int userId){
        return query.select(deliveryPartyMember.party)
                .from(deliveryPartyMember)
                .where(deliveryPartyMember.participant.id.eq(userId),
                        deliveryPartyMember.status.eq(BaseStatus.ACTIVE),
                        deliveryPartyMember.party.status.eq(BaseStatus.ACTIVE),
                        deliveryPartyMember.party.matchingStatus.eq(MatchingStatus.ONGOING),
                        deliveryPartyMember.party.orderTime.after(LocalDateTime.now())
                )
                .orderBy(deliveryPartyMember.party.createdAt.desc())
                .fetch();
    }

    //사용자가 진행했던(현재 비활성화)활동들 조회
    public GetEndedDeliveryPartiesRes getEndedDeliveryParties(int userId, Pageable pageable){

        List<DeliveryParty> deliveryParties = query.select(deliveryParty)
                .from(deliveryParty)
                .where(deliveryParty.chief.id.eq(userId),
                        deliveryParty.status.eq(BaseStatus.ACTIVE),
                        deliveryParty.matchingStatus.eq(MatchingStatus.FINISH),
                        deliveryParty.orderStatus.eq(OrderStatus.DELIVERY_COMPLETE))
                .orderBy(deliveryParty.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1) // 페이징을 위해 11개를 가져온다.
                .fetch();

        boolean isFinalPage = true;

        if(deliveryParties.size() == pageable.getPageSize() + 1){
            deliveryParties.remove(pageable.getPageSize());
            isFinalPage = false;
        }

        List<EndedDeliveryPartiesVo> endedDeliveryPartiesVoList =
                deliveryParties.stream()
                .map(EndedDeliveryPartiesVo::toDto)
                .collect(Collectors.toList());

        return new GetEndedDeliveryPartiesRes(isFinalPage, endedDeliveryPartiesVoList);
    }
}

//INACTIVE, REPORTED 조건 추가하기