package shop.geeksasang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.University;

import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Integer> {

    Optional<University> findUniversitiesByName(String name);
}
