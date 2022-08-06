package shop.geeksasang.unit.service;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyRes;
import shop.geeksasang.factory.domain.DormitoryFactory;
import shop.geeksasang.factory.domain.FoodCategoryFactory;
import shop.geeksasang.factory.domain.HashTagFactory;
import shop.geeksasang.factory.domain.MemberFactory;
import shop.geeksasang.factory.dto.deliveryparty.PostDeliveryPartyFactory;
import shop.geeksasang.repository.*;
import shop.geeksasang.service.DeliveryPartyService;

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
    @DisplayName("배달 파티 성공 테스트")
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

}
