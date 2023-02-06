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
import shop.geeksasang.domain.deliveryparty.FoodCategory;
import shop.geeksasang.domain.deliveryparty.HashTag;
import shop.geeksasang.domain.location.Location;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.domain.university.Dormitory;
import shop.geeksasang.domain.university.University;
import shop.geeksasang.dto.chat.chatmember.GetPartyChatRoomMembersInfoRes;
import shop.geeksasang.dto.chat.partychatroom.PartyChatRoomRes;
import shop.geeksasang.dto.deliveryParty.get.GetDeliveryPartyDetailRes;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyRes;
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

    University university;

    FoodCategory foodCategory;

    Dormitory dormitory;

    HashTag hashTag;

    @BeforeEach
    void beforeAll(){
        university = universityRepository.save(new University("예시대학교", "ex", "example"));
        foodCategory = foodCategoryRepository.save(new FoodCategory(1, "한식"));
        dormitory = dormitoryRepository.save(new Dormitory(1, university, "예시 1기숙사", new Location(1.22, 1.33)));
        hashTag= hashTagRepository.save(new HashTag(1, "나눠먹기"));
    }

    @Test
    @Transactional
    @DisplayName("배달파티 성공 테스트")
    void createDeliveryParty(){
        //given
        PostDeliveryPartyReq dto = PostDeliveryPartyFactory.createReq(foodCategory.getId());
        Email email1 = emailRepository.save(new Email("@gmail.com", ValidStatus.SUCCESS));

        Member member = memberRepository.save(new Member("debin", email1));

        //when
        PostDeliveryPartyRes result = deliveryPartyService.registerDeliveryParty(dto, member.getId(), dormitory.getId());

        //then
        assertThat(result.getTitle()).isEqualTo("party");
        assertThat(result.getContent()).isEqualTo("content");
    }
    
    
    @Test
    @Transactional
    @DisplayName("배달 파티 멤버 나가기")
    void deleteDeliveryPartyMember() throws JsonProcessingException {

        //given
        PostDeliveryPartyReq dto = PostDeliveryPartyFactory.createReq(foodCategory.getId());
        Email email1 = emailRepository.save(new Email("@gmail.com", ValidStatus.SUCCESS));
        Email email2 = emailRepository.save(new Email("321@gmail.com", ValidStatus.SUCCESS));

        Member member = memberRepository.save(new Member("debin", email1));
        Member member2 = memberRepository.save(new Member("neo", email2));


        PostDeliveryPartyRes deliveryParty = deliveryPartyService.registerDeliveryParty(dto, member.getId(), dormitory.getId());
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


    @Test
    @Transactional
    @DisplayName("방장 교체 나가기")
    void changeChief() throws JsonProcessingException {

        //given
        PostDeliveryPartyReq dto = PostDeliveryPartyFactory.createReq(foodCategory.getId());
        Email email1 = emailRepository.save(new Email("@gmail.com", ValidStatus.SUCCESS));
        Email email2 = emailRepository.save(new Email("321@gmail.com", ValidStatus.SUCCESS));
        Email email3 = emailRepository.save(new Email("4321@gmail.com", ValidStatus.SUCCESS));


        Member member = memberRepository.save(new Member("debin", email1));
        Member member2 = memberRepository.save(new Member("neo", email2));
        Member member3 = memberRepository.save(new Member("mini", email3));


        PostDeliveryPartyRes deliveryParty = deliveryPartyService.registerDeliveryParty(dto, member.getId(), dormitory.getId());
        deliveryPartyMemberService.joinDeliveryPartyMember(deliveryParty.getId(), member2.getId());
        deliveryPartyMemberService.joinDeliveryPartyMember(deliveryParty.getId(), member3.getId());


        PartyChatRoomRes chatRoom = deliveryPartyChatService.createChatRoom(member.getId(), "test", "111", "toss", "ex", 3, deliveryParty.getId());
        deliveryPartyChatService.joinPartyChatRoom(member2.getId(), chatRoom.getPartyChatRoomId(), LocalDateTime.now());
        deliveryPartyChatService.joinPartyChatRoom(member3.getId(), chatRoom.getPartyChatRoomId(), LocalDateTime.now());


        //when
        deliveryPartyChatService.changeChief(member.getId(), chatRoom.getPartyChatRoomId());
        deliveryPartyService.chiefLeaveDeliveryParty(deliveryParty.getId(), member.getId());

        GetDeliveryPartyDetailRes result = deliveryPartyService.getDeliveryPartyDetailById(deliveryParty.getId(), member.getId());
        List<GetPartyChatRoomMembersInfoRes> chatRoomMembersInfo = deliveryPartyChatService.getChatRoomMembersInfo(deliveryParty.getId(), member.getId(), chatRoom.getPartyChatRoomId());

        //then
        Assertions.assertThat(chatRoomMembersInfo.size()).isEqualTo(1);
        Assertions.assertThat(result.getCurrentMatching()).isEqualTo(2);
        Assertions.assertThat(result.getChiefId()).isEqualTo(member2.getId());
    }
}
