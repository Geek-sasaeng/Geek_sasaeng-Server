package shop.geeksasang.domain.report.list;

import shop.geeksasang.domain.DeliveryParty;

import javax.persistence.*;

@Entity
@DiscriminatorValue("delivery_party_report_list")
public class DeliverPartyReportList extends ReportList {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_delivery_party_id")
    private DeliveryParty reportedDeliveryParty;
}


//멤버가 배달 파티 중복 신고를 못하기 위해 만든 테이블