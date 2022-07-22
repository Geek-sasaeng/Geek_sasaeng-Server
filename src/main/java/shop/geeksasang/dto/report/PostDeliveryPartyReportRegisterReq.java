package shop.geeksasang.dto.report;

import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.ReportCategory;
import shop.geeksasang.domain.report.DeliveryPartyReport;

@Getter @Setter
public class PostDeliveryPartyReportRegisterReq {
    private int reportCategoryId;
    private int reportedDeliveryPartyId;
    private String reportContent;
    private String additionalContent;
    private boolean isBlock;

    public DeliveryPartyReport toEntity(Member member, DeliveryParty deliveryParty, ReportCategory reportCategory, PostDeliveryPartyReportRegisterReq dto) {
        return DeliveryPartyReport.builder()
                .reportingMember(member)
                .reportedMember(deliveryParty.getChief())
                .reportCategory(reportCategory)
                .reportContent(dto.getReportContent())
                .additionalContent(dto.getAdditionalContent())
                .deliveryParty(deliveryParty)
                .build();
    }
}
