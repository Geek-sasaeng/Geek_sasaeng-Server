package shop.geeksasang.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.ReportCategory;

@Repository
public interface ReportCategoryRepository extends JpaRepository <ReportCategory, Integer> {
}
