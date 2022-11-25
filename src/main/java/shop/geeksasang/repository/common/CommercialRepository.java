package shop.geeksasang.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.geeksasang.domain.commercial.Commercial;

public interface CommercialRepository extends JpaRepository<Commercial, Integer> {
}
