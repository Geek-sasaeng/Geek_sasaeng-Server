package shop.geeksasang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.Dormitory;
import shop.geeksasang.domain.FoodCategory;

import java.util.Optional;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory,Integer> {

    @Query("select f from FoodCategory f where f.status = 'ACTIVE'")
    Optional<FoodCategory> findFoodCategoryById(int id);
}
