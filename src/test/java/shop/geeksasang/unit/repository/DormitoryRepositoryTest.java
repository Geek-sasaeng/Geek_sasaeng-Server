package shop.geeksasang.unit.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import shop.geeksasang.domain.university.Dormitory;
import shop.geeksasang.domain.location.Location;
import shop.geeksasang.domain.university.University;
import shop.geeksasang.repository.university.DormitoryRepository;
import shop.geeksasang.repository.university.UniversityRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DormitoryRepositoryTest {

    @Autowired
    private DormitoryRepository dormitoryRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Test
    @DisplayName("대학교 id를 이용해 해당 대학교의 기숙사들을 가져오는 성공 케이스 테스트")
    void findDormitoryByUniversityId(){
        //given
        University gachon = new University("가천", "Gachon", "img");

        Dormitory dormitory1 = new Dormitory(gachon, "제 1기숙사", new Location(1.22, 1.33));
        Dormitory dormitory2 = new Dormitory(gachon, "제 2기숙사", new Location(1.32, 1.43));

        University saveUniversity = universityRepository.save(gachon);

        dormitoryRepository.save(dormitory1);
        dormitoryRepository.save(dormitory2);

        //when
        List<Dormitory> result = dormitoryRepository.findDormitoryByUniversityId(saveUniversity.getId());

        //then
        assertThat(result).containsExactly(dormitory1, dormitory2);

    }

}