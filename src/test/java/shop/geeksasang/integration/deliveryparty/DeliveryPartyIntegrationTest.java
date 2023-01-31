package shop.geeksasang.integration.deliveryparty;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyRes;
import shop.geeksasang.factory.dto.deliveryparty.PostDeliveryPartyFactory;
import shop.geeksasang.integration.IntegrationTest;
import shop.geeksasang.repository.member.MemberRepository;
import shop.geeksasang.service.deliveryparty.DeliveryPartyService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class DeliveryPartyIntegrationTest extends IntegrationTest {

    @Autowired
    DeliveryPartyService deliveryPartyService;

    @Autowired
    MemberRepository memberRepository;


    @Test
    @Transactional
    @DisplayName("배달파티 성공 테스트")
    void createDeliveryParty(){
        //given
        PostDeliveryPartyReq dto = PostDeliveryPartyFactory.createReq();

        Member member = memberRepository.save(new Member("debin"));
        int dormitoryId = 1;


        List<Member> all = memberRepository.findAll();
        //when
        PostDeliveryPartyRes result = deliveryPartyService.registerDeliveryParty(dto, member.getId(), dormitoryId);

        //then
        assertThat(result.getTitle()).isEqualTo("party");
        assertThat(result.getContent()).isEqualTo("content");
    }
}
