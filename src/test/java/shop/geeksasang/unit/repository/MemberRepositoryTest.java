package shop.geeksasang.unit.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.geeksasang.config.status.ValidStatus;
import shop.geeksasang.domain.*;
import shop.geeksasang.repository.*;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private DormitoryRepository dormitoryRepository;
    @Autowired
    private PhoneNumberRepository phoneNumberRepository;
    @Autowired
    private EmailRepository emailRepository;


    @Test
    @DisplayName("회원의 id(로그인id)를 이용해 멤버의 정보를 가져오는 성공 테스트 케이스 테스트")
    void findMemberByLoginId(){
        //given
        University university = new University("가천","emailAddress","imageUrl");
        Dormitory dormitory = new Dormitory(university,"제 1기숙사",new Location(37.456335,127.135331));
        PhoneNumber phoneNumber = new PhoneNumber("01022223333",ValidStatus.SUCCESS);
        Email email = new Email("emailAddress",ValidStatus.SUCCESS);
        Member member = Member
                .builder()
                .loginId("dkdlel")
                .nickName("닉네임")
                .password("qlalfqjsgh")
                .university(university)
                .phoneNumber(phoneNumber)
                .informationAgreeStatus("Y")
                .dormitory(dormitory)
                .email(email)
                .build();

        universityRepository.save(university);
        dormitoryRepository.save(dormitory);
        phoneNumberRepository.save(phoneNumber);
        emailRepository.save(email);

        Member saveMember = memberRepository.save(member);

        //when
        Optional<Member> findMember = memberRepository.findMemberByLoginId(saveMember.getLoginId());

        //then
        assertThat(member.getLoginId()).isEqualTo(findMember.get().getLoginId());
    }
}
