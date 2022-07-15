package shop.geeksasang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.geeksasang.domain.HashTag;

public interface HashTagRepository extends JpaRepository<HashTag,Integer> {
}
