package shop.geeksasang.unit.service;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import shop.geeksasang.config.type.OrderTimeCategoryType;
import shop.geeksasang.dto.deliveryParty.get.GetDeliveryPartiesRes;
import shop.geeksasang.dto.deliveryParty.get.vo.DeliveryPartiesVo;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyRes;
import shop.geeksasang.factory.domain.DormitoryFactory;
import shop.geeksasang.factory.domain.FoodCategoryFactory;
import shop.geeksasang.factory.domain.HashTagFactory;
import shop.geeksasang.factory.domain.MemberFactory;
import shop.geeksasang.factory.dto.deliveryparty.PostDeliveryPartyFactory;
import shop.geeksasang.repository.*;
import shop.geeksasang.service.DeliveryPartyService;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class DeliveryPartyServiceTest {

    @InjectMocks
    private DeliveryPartyService deliveryPartyService;

    @Mock
    private DeliveryPartyRepository deliveryPartyRepository;

    @Mock
    private DeliveryPartyMemberRepository deliveryPartyMemberRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private DormitoryRepository dormitoryRepository;

    @Mock
    private FoodCategoryRepository foodCategoryRepository;

    @Mock
    private HashTagRepository hashTagRepository;

    @Mock
    private DeliveryPartyQueryRepository deliveryPartyQueryRepository;

    @Mock
    private BlockRepository blockRepository;

    @Test
    @DisplayName("배달 파티 생성 성공 테스트")
    void successCreateDeliveryPartyTest(){
        //given
        PostDeliveryPartyReq dto = PostDeliveryPartyFactory.createReq();
        int chiefId = 1;
        int dormitoryId = 1;

        given(memberRepository.findById(chiefId)).willReturn(Optional.of(MemberFactory.create()));
        given(foodCategoryRepository.findFoodCategoryById(dto.getFoodCategory())).willReturn(Optional.of(FoodCategoryFactory.create()));
        given(dormitoryRepository.findDormitoryById(dormitoryId)).willReturn(Optional.of(DormitoryFactory.create()));
        given(hashTagRepository.findById(1)).willReturn(Optional.of(HashTagFactory.create()));

        //when
        PostDeliveryPartyRes party = deliveryPartyService.registerDeliveryParty(dto, chiefId, dormitoryId);

        //then
        assertThat(party.getTitle()).isEqualTo("party");
        assertThat(party.getContent()).isEqualTo("content");
    }


//    @Test
//    @DisplayName("배달 파티 전체조회 성공 테스트")
//    void successGetDeliveryPartiesTest() {
//        int chiefId;
//        int dormitoryId;
//        List<DeliveryPartiesVo> deliveryPartiesVos;
//
//        //given
//        // 파티 리스트 생성
//        for(int i=0;i<10;i++){
//            PostDeliveryPartyReq dto = PostDeliveryPartyFactory.createReq();
//            chiefId = 1;
//            dormitoryId = 1;
//
//
//
//            deliveryPartyService.registerDeliveryParty(dto, chiefId, dormitoryId);
//        }
//
//
//        //int dormitoryId = 1;
//        int cursor =0;
//        String orderTimeCategory = OrderTimeCategoryType.DINNER.toString();
//        int maxMatching = 2;
//        int memberId = 1;
//
//        // 시나리오 명시
//
//        //when
//        GetDeliveryPartiesRes parties = deliveryPartyService.getDeliveryParties(dormitoryId, cursor, orderTimeCategory, maxMatching, memberId);
//
//        //then
//        //assertThat(list).contains(1, 2);
//        assertThat(parties.isFinalPage()).isEqualTo(true);
//        assertThat(parties.getDeliveryPartiesVoList()).contains("아이템 리스트");
//    }

}
