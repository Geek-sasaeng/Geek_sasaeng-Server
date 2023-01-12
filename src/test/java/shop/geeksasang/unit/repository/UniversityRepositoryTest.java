package shop.geeksasang.unit.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import shop.geeksasang.domain.university.University;
import shop.geeksasang.repository.university.UniversityRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //내장 디비가 아닌 실제 우리의 경우 rds를 사용
class UniversityRepositoryTest {

    @Autowired
    private UniversityRepository universityRepository;

    @Test
    @DisplayName("대학 인스턴스를 대학 이름로 찾는 성공 테스트")
    void successFindByUniversityName(){
        //given
        University university = new University("가천", "Gachon", "img");
        universityRepository.save(university);

        //when
        Optional<University> findUniversity = universityRepository.findUniversityByName("가천");

        //then
        assertThat(university.getName()).isEqualTo(findUniversity.get().getName());
    }

    @Test
    @DisplayName("대학 인스턴스를 대학 이름로 찾는 실패 테스트")
    void failFindByUniversityName(){
        //given
        University sungshin = new University("성신", "Sungshin", "img");
        University sejong = new University("세종", "Sejong", "img");
        universityRepository.save(sungshin);
        universityRepository.save(sejong);

        //when
        Optional<University> findUniversity = universityRepository.findUniversityByName("세종");

        //then
        assertThat(sungshin.getName()).isNotEqualTo(findUniversity.get().getName());
    }
}

/**
 * DataJpaTest는 Spring Data JPA 관련 테스트를 쉽게 할 수 있도록 도와주는 애노테이션. 데이터소스도 등록하고 여기서는 의존관계 주입도 가능하게 해준다.
 * 의존관계 주입이 가능하지만, 모든 빈들을 주입하지는 않아 @SpringBootTest보다도 상대적으로 빠르다.
 * 테스트 마무리시 Rollback도 해주며 다양한 편의 기능 제공. but 순수 JPA 테스트는 안되는 걸로 알고 있음.
 * 특히 DataJpaTest는 내장 데이터베이스를 사용해 편리함을 제공하는데, h2 같은 내장 데베 설정은 또 팀원들마다 문제가 있을 수 있어 테스트 데이터베이스를 사용하는 것으로 결정.
 *
 */