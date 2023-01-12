package shop.geeksasang.domain.report;

import lombok.Builder;
import lombok.NoArgsConstructor;
import shop.geeksasang.domain.deliveryparty.DeliveryParty;
import shop.geeksasang.domain.member.Member;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@DiscriminatorValue("delivery_party_report")
public class DeliveryPartyReport extends Report {

    @ManyToOne
    @JoinColumn(name = "delivery_party_id")
    private DeliveryParty deliveryParty;

    @Builder
    public DeliveryPartyReport(Member reportingMember, Member reportedMember, String reportContent, ReportCategory reportCategory, String additionalContent, DeliveryParty deliveryParty) {
        super(reportingMember, reportedMember, reportContent, reportCategory, additionalContent);
        this.deliveryParty = deliveryParty;
    }
}
