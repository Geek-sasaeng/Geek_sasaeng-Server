package shop.geeksasang.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.Domitory;
import shop.geeksasang.domain.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface DomitoryRepository extends JpaRepository<Domitory, Integer> {

    List<Domitory> findDomitoriesByUniversityId(int universityId);
}
