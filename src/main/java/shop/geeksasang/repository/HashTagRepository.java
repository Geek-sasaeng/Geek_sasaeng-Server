package shop.geeksasang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.HashTag;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag,Integer> {
}
