package shop.geeksasang.repository.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.report.ReportCategory;

@Repository
public interface ReportCategoryRepository extends JpaRepository <ReportCategory, Integer> {
}
