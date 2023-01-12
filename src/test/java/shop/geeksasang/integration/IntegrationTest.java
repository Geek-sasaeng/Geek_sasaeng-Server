package shop.geeksasang.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.domain.deliveryparty.FoodCategory;
import shop.geeksasang.domain.deliveryparty.HashTag;
import shop.geeksasang.domain.location.Location;
import shop.geeksasang.domain.university.Dormitory;
import shop.geeksasang.domain.university.University;
import shop.geeksasang.repository.university.DormitoryRepository;
import shop.geeksasang.repository.deliveryparty.FoodCategoryRepository;
import shop.geeksasang.repository.HashTagRepository;
import shop.geeksasang.repository.university.UniversityRepository;

@WebAppConfiguration
@SpringBootTest
@Transactional
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class IntegrationTest {

    @Autowired
    protected FoodCategoryRepository foodCategoryRepository;

    @Autowired
    protected DormitoryRepository dormitoryRepository;

    @Autowired
    protected HashTagRepository hashTagRepository;

    @Autowired
    protected UniversityRepository universityRepository;



    @BeforeEach
    void beforeAll(){
        University ex = universityRepository.save(new University("예시대학교", "ex", "example"));
        foodCategoryRepository.save(new FoodCategory(1, "한식"));
        dormitoryRepository.save(new Dormitory(1, ex, "예시 1기숙사", new Location(1.22, 1.33)));
        hashTagRepository.save(new HashTag(1, "나눠먹기"));
    }
}
