package shop.geeksasang.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.Domitory;

import java.util.List;
import java.util.Optional;

@Repository
public interface DomitoryRepository extends JpaRepository<Domitory, Integer> {

    @Query("select d from Domitory d join fetch d.university")
    Optional<Domitory> findByUniversity_id(int university_id);

    @Query("select d from Domitory d join fetch d.university")
    List<Domitory> findDomiByUniversity_id(int university_id);

}
