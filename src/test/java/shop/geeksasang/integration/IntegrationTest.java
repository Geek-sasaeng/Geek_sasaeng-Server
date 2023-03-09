package shop.geeksasang.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.domain.deliveryparty.FoodCategory;
import shop.geeksasang.domain.deliveryparty.HashTag;
import shop.geeksasang.domain.location.Location;
import shop.geeksasang.domain.university.Dormitory;
import shop.geeksasang.domain.university.University;
import shop.geeksasang.repository.auth.EmailRepository;
import shop.geeksasang.repository.chat.ChatRoomRepository;
import shop.geeksasang.repository.chat.PartyChatRoomMemberRepository;
import shop.geeksasang.repository.chat.PartyChatRoomRepository;
import shop.geeksasang.repository.member.GradeRepository;
import shop.geeksasang.repository.member.MemberRepository;
import shop.geeksasang.repository.university.DormitoryRepository;
import shop.geeksasang.repository.deliveryparty.FoodCategoryRepository;
import shop.geeksasang.repository.HashTagRepository;
import shop.geeksasang.repository.university.UniversityRepository;
import shop.geeksasang.service.chat.DeliveryPartyChatService;
import shop.geeksasang.service.deliveryparty.DeliveryPartyMemberService;
import shop.geeksasang.service.deliveryparty.DeliveryPartyService;

@WebAppConfiguration
@SpringBootTest
@Transactional
public abstract class IntegrationTest {

    @Autowired
    protected FoodCategoryRepository foodCategoryRepository;

    @Autowired
    protected DormitoryRepository dormitoryRepository;

    @Autowired
    protected HashTagRepository hashTagRepository;

    @Autowired
    protected UniversityRepository universityRepository;

    @Autowired
    protected DeliveryPartyService deliveryPartyService;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected DeliveryPartyMemberService deliveryPartyMemberService;

    @Autowired
    protected DeliveryPartyChatService deliveryPartyChatService;

    @Autowired
    protected EmailRepository emailRepository;

    @Autowired
    protected GradeRepository gradeRepository;

    @Autowired
    protected PartyChatRoomRepository chatRoomRepository;

    @Autowired
    protected PartyChatRoomMemberRepository chatRoomMemberRepository;
}
