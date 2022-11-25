package shop.geeksasang.repository.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shop.geeksasang.domain.report.DeliveryPartyReport;


@Repository
public interface DeliveryPartyReportRepository extends JpaRepository <DeliveryPartyReport, Integer> {
}
