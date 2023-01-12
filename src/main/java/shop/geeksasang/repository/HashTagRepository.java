package shop.geeksasang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.geeksasang.domain.deliveryparty.HashTag;

public interface HashTagRepository extends JpaRepository<HashTag,Integer> {
}
