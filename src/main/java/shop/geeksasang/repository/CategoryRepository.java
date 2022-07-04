package shop.geeksasang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
