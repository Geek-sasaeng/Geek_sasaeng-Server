package shop.geeksasang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.geeksasang.domain.FoodCategory;
import shop.geeksasang.domain.HashTag;

import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag,Integer> {
    @Query("select h from HashTag h where h.status = 'ACTIVE'")
    Optional<HashTag> findHashTagById(int id);
}
