package shop.geeksasang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.University;

import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Integer> {

    Optional<University> findUniversityByName(String name);


    @Query("select u from University u join fetch u.domitories where u.id = :universityId")
    Optional<University> findDomitoriesByUniversityId(int universityId);

    Optional<University> findById(int university_id);

}
