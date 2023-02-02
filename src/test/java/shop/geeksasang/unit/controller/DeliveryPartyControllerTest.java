package shop.geeksasang.unit.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyRes;
import shop.geeksasang.factory.dto.deliveryparty.PostDeliveryPartyFactory;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DeliveryPartyControllerTest extends ControllerTest {

    @Test
    @DisplayName("배달 파티 생성 성공 테스트")
    void registerDeliveryParty() throws Exception {
        int dormitoryId = 1;
        PostDeliveryPartyReq req = PostDeliveryPartyFactory.createReq(1);
        PostDeliveryPartyRes res = PostDeliveryPartyFactory.createRes();

        String value = mapper.writeValueAsString(req);


        given(deliveryPartyService.registerDeliveryParty(Mockito.any(PostDeliveryPartyReq.class), Mockito.any(Integer.class), Mockito.any(Integer.class))).willReturn(res);

        //given(this.deliveryPartyService.registerDeliveryParty(req, 1, 1)).willReturn(res); //이렇게 직접적인 값을 주면 오류 발생. 이걸로 한참을 헤맴.

        mvc.perform(post("/{dormitoryId}/delivery-party", dormitoryId)
                        //.header("jwtInfo", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(value)
        )
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }
}


//@DirtiesContext
//위 어노테이션을 통해 테스트를 수행하기 전, 수행한 이후, 그리고 테스트의 각 테스트 케이스마다 수행하기 전, 수행한 이후에 context를 다시 생성하도록 지시할 수 있다.
//@DirtiesContext