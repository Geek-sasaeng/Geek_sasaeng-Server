package shop.geeksasang.domain.report.record;

import lombok.NoArgsConstructor;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.Member;

import javax.persistence.*;

@Entity
@DiscriminatorValue("delivery_party_report_record")
@NoArgsConstructor
public class DeliverPartyReportRecord extends ReportRecord {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_delivery_party_id")
    private DeliveryParty reportedDeliveryParty;

    public DeliverPartyReportRecord(Member owner, DeliveryParty reportedDeliveryParty) {
        super(owner);
        this.reportedDeliveryParty = reportedDeliveryParty;
    }

    public boolean sameParty(DeliveryParty deliveryParty) {
        return reportedDeliveryParty == deliveryParty;
    }
}


//멤버가 배달 파티 중복 신고를 못하기 위해 만든 테이블