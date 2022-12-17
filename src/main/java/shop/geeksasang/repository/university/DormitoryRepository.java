package shop.geeksasang.repository.university;


import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.university.Dormitory;

import java.util.List;
import java.util.Optional;

@Repository
public interface DormitoryRepository extends JpaRepository<Dormitory, Integer> {

    @Query("select d from Dormitory d where d.university.id = :universityId")
    List<Dormitory> findDormitoryByUniversityId(int universityId);

    @Query("select d from Dormitory d where d.id = :dormitoryId and d.status = 'ACTIVE' ")
    Optional<Dormitory> findDormitoryById(@Param("dormitoryId") int dormitoryId);

}
