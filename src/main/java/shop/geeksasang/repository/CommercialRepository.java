package shop.geeksasang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.geeksasang.domain.Commercial;

public interface CommercialRepository extends JpaRepository<Commercial, Integer> {
}
