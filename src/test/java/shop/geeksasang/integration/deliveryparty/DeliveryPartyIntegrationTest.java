package shop.geeksasang.integration.deliveryparty;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyRes;
import shop.geeksasang.factory.dto.deliveryparty.PostDeliveryPartyFactory;
import shop.geeksasang.integration.IntegrationTest;
import shop.geeksasang.repository.MemberRepository;
import shop.geeksasang.service.DeliveryPartyService;

import static org.assertj.core.api.Assertions.*;

public class DeliveryPartyIntegrationTest extends IntegrationTest {

    @Autowired
    DeliveryPartyService deliveryPartyService;

    @Autowired
    MemberRepository memberRepository;

    Member member;

    @BeforeEach
    void beforeEach(){
        member = memberRepository.save(new Member("debin"));
    }

    @Test
    @DisplayName("배달파티 성공 테스트")
    void createDeliveryParty(){
        //given
        PostDeliveryPartyReq dto = PostDeliveryPartyFactory.createReq();
        int chiefId = member.getId();
        int dormitoryId = 1;

        //when
        PostDeliveryPartyRes result = deliveryPartyService.registerDeliveryParty(dto, chiefId, dormitoryId);

        //then
        assertThat(result.getTitle()).isEqualTo("party");
        assertThat(result.getContent()).isEqualTo("content");

    }
}
