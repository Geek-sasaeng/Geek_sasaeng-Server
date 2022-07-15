package shop.geeksasang.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.Dormitory;

import java.util.List;

@Repository
public interface DormitoryRepository extends JpaRepository<Dormitory, Integer> {

    @Query("select d from Dormitory d where d.university.id = :universityId")
    List<Dormitory> findDormitoryByUniversityId(int universityId);

}
