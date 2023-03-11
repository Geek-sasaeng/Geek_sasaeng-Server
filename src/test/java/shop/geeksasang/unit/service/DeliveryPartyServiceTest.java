package shop.geeksasang.unit.service;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shop.geeksasang.dto.chat.PostChatRes;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyRes;
import shop.geeksasang.factory.domain.DormitoryFactory;
import shop.geeksasang.factory.domain.FoodCategoryFactory;
import shop.geeksasang.factory.domain.HashTagFactory;
import shop.geeksasang.factory.domain.MemberFactory;
import shop.geeksasang.factory.dto.deliveryparty.PostDeliveryPartyFactory;
import shop.geeksasang.repository.*;
import shop.geeksasang.repository.block.BlockRepository;
import shop.geeksasang.repository.deliveryparty.DeliveryPartyMemberRepository;
import shop.geeksasang.repository.deliveryparty.query.DeliveryPartyQueryRepository;
import shop.geeksasang.repository.deliveryparty.DeliveryPartyRepository;
import shop.geeksasang.repository.deliveryparty.FoodCategoryRepository;
import shop.geeksasang.repository.member.MemberRepository;
import shop.geeksasang.repository.university.DormitoryRepository;
import shop.geeksasang.service.deliveryparty.DeliveryPartyService;

import java.util.Optional;

@RabbitListenerTest
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DeliveryPartyServiceTest {

    @InjectMocks
    private DeliveryPartyService deliveryPartyService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private DormitoryRepository dormitoryRepository;

    @Mock
    private FoodCategoryRepository foodCategoryRepository;

    @Mock
    private HashTagRepository hashTagRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpAdmin amqpAdmin;

    @Spy
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("배달 파티 생성 성공 테스트")
    void successCreateDeliveryPartyTest(){
        //given
        PostDeliveryPartyReq dto = PostDeliveryPartyFactory.createReq(1);
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

    @Test
    @DisplayName("강제 퇴장 당한 사용자에게 메시지 전송 테스트")
    void success_sendBanMessageToBanUser(){
        // given
        final String memberId = "1";
        final String chatRoomTitle = "피자 파티";

        Queue userQueue = new Queue(memberId);
        amqpAdmin.declareQueue(userQueue);

        final String EXIT_EXCHANGE_NAME = "dx.exit";
        DirectExchange exitExchange = new DirectExchange(EXIT_EXCHANGE_NAME);

        final String EXIT_BINDING_KEY = memberId + ".exit";
        Binding binding = BindingBuilder.bind(userQueue)
                .to(exitExchange)
                .with(EXIT_BINDING_KEY);
        amqpAdmin.declareBinding(binding);

        PostChatRes postChatRes = new PostChatRes(chatRoomTitle);

        String jsonMessage = null;
        try {
            jsonMessage = objectMapper.writeValueAsString(postChatRes);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // when
        rabbitTemplate.convertAndSend(EXIT_EXCHANGE_NAME, EXIT_BINDING_KEY, jsonMessage);
    }

    // 강제 퇴장 큐 리스너 테스트
//    @Test
//    @DisplayName("강제 퇴장 당한 사용자에게 메시지 전송 테스트")
//    @RabbitListener(queues = "1")
//    public void receiveBanMessage(final String message) {
//        // given
//        PostChatRes postChatRes = null;
//        try {
//            postChatRes = objectMapper.readValue(message, PostChatRes.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        // then
//        assertThat(postChatRes.getChatType().equals("ban"));
//    }


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
