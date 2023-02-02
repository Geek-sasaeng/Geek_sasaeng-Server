package shop.geeksasang.integration.deliveryparty;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.status.ValidStatus;
import shop.geeksasang.domain.auth.Email;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.dto.chat.chatmember.GetPartyChatRoomMembersInfoRes;
import shop.geeksasang.dto.chat.chatmember.PartyChatRoomMemberRes;
import shop.geeksasang.dto.chat.partychatroom.PartyChatRoomRes;
import shop.geeksasang.dto.deliveryParty.get.GetDeliveryPartyDetailRes;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyRes;
import shop.geeksasang.dto.deliveryPartyMember.post.PostDeliveryPartyMemberRes;
import shop.geeksasang.factory.dto.deliveryparty.PostDeliveryPartyFactory;
import shop.geeksasang.integration.IntegrationTest;
import shop.geeksasang.repository.auth.EmailRepository;
import shop.geeksasang.repository.member.MemberRepository;
import shop.geeksasang.service.chat.DeliveryPartyChatService;
import shop.geeksasang.service.deliveryparty.DeliveryPartyMemberService;
import shop.geeksasang.service.deliveryparty.DeliveryPartyService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class DeliveryPartyIntegrationTest extends IntegrationTest {

    @Autowired
    DeliveryPartyService deliveryPartyService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    DeliveryPartyMemberService deliveryPartyMemberService;

    @Autowired
    DeliveryPartyChatService deliveryPartyChatService;

    @Autowired
    EmailRepository emailRepository;


    @Test
    @Transactional
    @DisplayName("배달파티 성공 테스트")
    void createDeliveryParty(){
        //given
        PostDeliveryPartyReq dto = PostDeliveryPartyFactory.createReq();
        Member member = memberRepository.save(new Member("debin", new Email("adddress", ValidStatus.SUCCESS)));
        int dormitoryId = 1;

        //when
        PostDeliveryPartyRes result = deliveryPartyService.registerDeliveryParty(dto, member.getId(), dormitoryId);

        //then
        assertThat(result.getTitle()).isEqualTo("party");
        assertThat(result.getContent()).isEqualTo("content");
    }
    
    
    @Test
    @Transactional
    void deleteDeliveryPartyMember() throws JsonProcessingException {

        //given
        PostDeliveryPartyReq dto = PostDeliveryPartyFactory.createReq();
        Email email1 = emailRepository.save(new Email("@gmail.com", ValidStatus.SUCCESS));
        Email email2 = emailRepository.save(new Email("321@gmail.com", ValidStatus.SUCCESS));

        Member member = memberRepository.save(new Member("debin", email1));
        Member member2 = memberRepository.save(new Member("neo", email2));


        PostDeliveryPartyRes deliveryParty = deliveryPartyService.registerDeliveryParty(dto, member.getId(), 1);
        deliveryPartyMemberService.joinDeliveryPartyMember(deliveryParty.getId(), member2.getId());


        PartyChatRoomRes chatRoom = deliveryPartyChatService.createChatRoom(member.getId(), "test", "111", "toss", "ex", 3, deliveryParty.getId());
        deliveryPartyChatService.joinPartyChatRoom(member2.getId(), chatRoom.getPartyChatRoomId(), LocalDateTime.now());

        //when
        deliveryPartyChatService.removeMember(member2.getId(), chatRoom.getPartyChatRoomId());
        deliveryPartyMemberService.patchDeliveryPartyMemberStatus(deliveryParty.getId(), member2.getId());

        GetDeliveryPartyDetailRes result = deliveryPartyService.getDeliveryPartyDetailById(deliveryParty.getId(), member.getId());
        List<GetPartyChatRoomMembersInfoRes> chatRoomMembersInfo = deliveryPartyChatService.getChatRoomMembersInfo(deliveryParty.getId(), member.getId(), chatRoom.getPartyChatRoomId());

        //then
        Assertions.assertThat(chatRoomMembersInfo.size()).isEqualTo(0);
        Assertions.assertThat(result.getCurrentMatching()).isEqualTo(1);
    }
}
