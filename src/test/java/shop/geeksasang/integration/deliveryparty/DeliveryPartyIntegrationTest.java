package shop.geeksasang.integration.deliveryparty;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.AnnotationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.status.MatchingStatus;
import shop.geeksasang.config.status.ValidStatus;
import shop.geeksasang.domain.auth.Email;
import shop.geeksasang.domain.deliveryparty.FoodCategory;
import shop.geeksasang.domain.deliveryparty.HashTag;
import shop.geeksasang.domain.location.Location;
import shop.geeksasang.domain.member.Grade;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.domain.university.Dormitory;
import shop.geeksasang.domain.university.University;
import shop.geeksasang.dto.chat.chatmember.GetPartyChatRoomMembersInfoRes;
import shop.geeksasang.dto.chat.partychatroom.PartyChatRoomRes;
import shop.geeksasang.dto.deliveryParty.get.GetDeliveryPartyDetailRes;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyRes;
import shop.geeksasang.dto.deliveryParty.put.PutDeliveryPartyReq;
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

    Grade grade;

    Email email1;
    Email email2;
    Email email3;
    Email email4;


    Member member;
    Member member2;
    Member member3;
    Member member4;

    @BeforeEach
    void beforeAll(){
        university = universityRepository.save(new University("예시대학교", "ex", "example"));
        foodCategory = foodCategoryRepository.save(new FoodCategory(1, "한식"));
        dormitory = dormitoryRepository.save(new Dormitory(1, university, "예시 1기숙사", new Location(1.22, 1.33)));
        hashTag= hashTagRepository.save(new HashTag(1, "나눠먹기"));
        grade = gradeRepository.save(new Grade("test" , 0));

        email1 = emailRepository.save(new Email("@gmail.com", ValidStatus.SUCCESS));
        email2 = emailRepository.save(new Email("321@gmail.com", ValidStatus.SUCCESS));
        email3 = emailRepository.save(new Email("4321@gmail.com", ValidStatus.SUCCESS));
        email4 = emailRepository.save(new Email("1234321@gmail.com", ValidStatus.SUCCESS));


        member = memberRepository.save(new Member("debin", email1, grade));
        member2 = memberRepository.save(new Member("neo", email2, grade));
        member3 = memberRepository.save(new Member("mini", email3, grade));
        member4 = memberRepository.save(new Member("thomas", email4, grade));
    }

    @Test
    @DisplayName("배달파티 성공 테스트")
    void createDeliveryParty(){
        //given
        PostDeliveryPartyReq dto = PostDeliveryPartyFactory.createReq(foodCategory.getId());

        //when
        PostDeliveryPartyRes result = deliveryPartyService.registerDeliveryParty(dto, member.getId(), dormitory.getId());

        //then
        assertThat(result.getTitle()).isEqualTo("party");
        assertThat(result.getContent()).isEqualTo("content");
    }
    
    
    @Test
    @DisplayName("배달 파티 멤버 나가기")
    void deleteDeliveryPartyMember() throws JsonProcessingException {

        //given
        PostDeliveryPartyReq dto = PostDeliveryPartyFactory.createReq(foodCategory.getId());

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
        assertThat(chatRoomMembersInfo.size()).isEqualTo(0);
        assertThat(result.getCurrentMatching()).isEqualTo(1);
    }


    @Test
    @DisplayName("방장 교체 나가기")
    void changeChief() throws JsonProcessingException {

        //given
        PostDeliveryPartyReq dto = PostDeliveryPartyFactory.createReq(foodCategory.getId());


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
        assertThat(chatRoomMembersInfo.size()).isEqualTo(1);
        assertThat(result.getCurrentMatching()).isEqualTo(2);
        assertThat(result.getChiefId()).isEqualTo(member2.getId());
    }


    @Test
    @DisplayName("멤버 나가고 교체후 상태가 잘 변하는 지 점검")
    void deliveryPartyFullTest() throws JsonProcessingException {

        //given
        PostDeliveryPartyReq dto = PostDeliveryPartyFactory.createReqV2(foodCategory.getId());

        PostDeliveryPartyRes deliveryParty = deliveryPartyService.registerDeliveryParty(dto, member.getId(), dormitory.getId());
        PartyChatRoomRes chatRoom = deliveryPartyChatService.createChatRoom(member.getId(), "test", "111", "toss", "ex", 3, deliveryParty.getId());

        deliveryPartyMemberService.joinDeliveryPartyMember(deliveryParty.getId(), member2.getId());
        deliveryPartyChatService.joinPartyChatRoom(member2.getId(), chatRoom.getPartyChatRoomId(), LocalDateTime.now());

        deliveryPartyMemberService.joinDeliveryPartyMember(deliveryParty.getId(), member4.getId());
        deliveryPartyChatService.joinPartyChatRoom(member4.getId(), chatRoom.getPartyChatRoomId(), LocalDateTime.now());

        //when
        deliveryPartyMemberService.patchDeliveryPartyMemberStatus(deliveryParty.getId(), member2.getId());
        deliveryPartyChatService.removeMember(member2.getId(), chatRoom.getPartyChatRoomId());

        deliveryPartyMemberService.joinDeliveryPartyMember(deliveryParty.getId(), member3.getId());
        deliveryPartyChatService.joinPartyChatRoom(member3.getId(), chatRoom.getPartyChatRoomId(), LocalDateTime.now());


        GetDeliveryPartyDetailRes result = deliveryPartyService.getDeliveryPartyDetailById(deliveryParty.getId(), member.getId());

        //then
        assertThat(result.getCurrentMatching()).isEqualTo(3);
        assertThat(result.getMatchingStatus()).isSameAs(MatchingStatus.FINISH);
    }


    @Test
    @DisplayName("배달 파티 업데이트 하기")
    void changeStatus() throws JsonProcessingException {

        //given
        PostDeliveryPartyReq dto1 = PostDeliveryPartyFactory.createReqV2(foodCategory.getId());

        PostDeliveryPartyRes deliveryParty = deliveryPartyService.registerDeliveryParty(dto1, member.getId(), dormitory.getId());
        PartyChatRoomRes chatRoom = deliveryPartyChatService.createChatRoom(member.getId(), "test", "111", "toss", "ex", 3, deliveryParty.getId());
        System.out.println("chatRoom.getPartyChatRoomId() = " + chatRoom.getPartyChatRoomId());
        deliveryPartyMemberService.joinDeliveryPartyMember(deliveryParty.getId(), member2.getId());
        deliveryPartyChatService.joinPartyChatRoom(member2.getId(), chatRoom.getPartyChatRoomId(), LocalDateTime.now());

        //when
        PutDeliveryPartyReq dto2 = new PutDeliveryPartyReq();
        dto2.setMaxMatching(2);
        dto2.setFoodCategory(foodCategory.getId());
        dto2.setOrderTime(LocalDateTime.now());

        deliveryPartyService.updateDeliveryParty(dto2, member.getId(), dormitory.getId(), deliveryParty.getId());
        deliveryPartyChatService.updateRoom(2, member.getId(), chatRoom.getPartyChatRoomId());

        GetDeliveryPartyDetailRes result = deliveryPartyService.getDeliveryPartyDetailById(deliveryParty.getId(), member.getId());

        //then
        assertThat(result.getCurrentMatching()).isEqualTo(2);
        assertThat(result.getChiefId()).isEqualTo(member.getId());
        assertThat(result.getMatchingStatus()).isEqualTo(MatchingStatus.FINISH);
    }
}
