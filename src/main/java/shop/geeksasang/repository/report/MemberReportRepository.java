package shop.geeksasang.repository.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shop.geeksasang.domain.report.MemberReport;

@Repository
public interface MemberReportRepository extends JpaRepository <MemberReport, Integer> {
}
