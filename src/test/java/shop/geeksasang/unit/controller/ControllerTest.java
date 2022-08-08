package shop.geeksasang.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import shop.geeksasang.controller.*;
import shop.geeksasang.service.*;
import shop.geeksasang.utils.jwt.JwtService;

@WebMvcTest({
        DeliveryPartyController.class,
        CommercialController.class,
        DeliveryPartyMemberController.class,
        DeliveryPartyReportController.class,
        DormitoryController.class,
        EmailController.class,
        LoginController.class,
        MemberController.class,
        MemberReportController.class,
        SmsController.class,
        UniversityController.class
})
@MockBean(JpaMetamodelMappingContext.class) //JPA 설정을 못하므로 오류가 발생. 따라서 해당 애노테이션을 넣는다.
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper mapper;

    @MockBean
    protected JwtService jwtService;

    @MockBean
    protected DeliveryPartyService deliveryPartyService;

    @MockBean
    protected CommercialService commercialService;

    @MockBean
    protected BlockService blockService;

    @MockBean
    protected DeliveryPartyMemberService deliveryPartyMemberService;

    @MockBean
    protected DeliveryPartyReportService deliveryPartyReportService;

    @MockBean
    protected DormitoryService dormitoryService;

    @MockBean
    protected EmailService emailService;

    @MockBean
    protected LoginService loginService;

    @MockBean
    protected MemberReportService memberReportService;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected SmsService smsService;

    @MockBean
    protected UniversityService universityService;
}

/**
 * 전체 애플리케이션 구성을 로드하고 MockMVC를 사용하려는 경우 @SpringBoot을 고려해야 합니다.이 주석이 아닌 @AutoConfigureMockMvc와 결합된 테스트입니다.
 *
 */