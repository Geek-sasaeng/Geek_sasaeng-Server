package shop.geeksasang.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.Domitory;

import java.util.List;
import java.util.Optional;

@Repository
public interface DomitoryRepository extends JpaRepository<Domitory, Integer> {

    @Query("select d from Domitory d where d.university.id = :universityId")
    List<Domitory> findDomitoryByUniversityId(int universityId);

}
