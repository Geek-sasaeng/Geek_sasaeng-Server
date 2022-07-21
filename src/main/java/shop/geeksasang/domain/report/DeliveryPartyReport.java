package shop.geeksasang.domain.report;

import lombok.Builder;
import lombok.NoArgsConstructor;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.ReportCategory;

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

/**
 * 게시물 신고
 * 게시글 신고가 3개 이상일 때는, 그 게시글 삭제
 * 이미 신고를 한 게시물에는 다시 신고를 할 수 없음
 */