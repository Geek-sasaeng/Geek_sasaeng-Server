package shop.geeksasang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
}
