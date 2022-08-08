package shop.geeksasang.unit.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shop.geeksasang.repository.DeliveryPartyQueryRepository;


@Import(DeliveryPartyQueryRepository.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeliveryPartyQueryRepositoryTest {

    @Autowired
    DeliveryPartyQueryRepository queryRepository;

    @Test
    void findDeliveryPartiesByConditions() {
        System.out.println("queryRepository = " + queryRepository);
    }

    @Test
    void getDeliveryPartiesByKeyword2() {
    }
}