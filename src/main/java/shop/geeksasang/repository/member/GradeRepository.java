package shop.geeksasang.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.member.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
}
