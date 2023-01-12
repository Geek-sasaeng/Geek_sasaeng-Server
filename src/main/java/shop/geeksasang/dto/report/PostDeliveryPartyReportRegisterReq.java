package shop.geeksasang.dto.report;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import shop.geeksasang.domain.deliveryparty.DeliveryParty;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.domain.report.ReportCategory;
import shop.geeksasang.domain.report.DeliveryPartyReport;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class PostDeliveryPartyReportRegisterReq {

    @ApiModelProperty(example = "9")
    @ApiParam(value = "신고 카테고리 id, 0 ~ 10 까지의 int 값.", required = true)
    @Range(min = 0, max = 10)
    private int reportCategoryId;

    @ApiModelProperty(example = "34")
    @ApiParam(value = "차단할 멤버 id", required = true)
    @NotNull
    private int reportedMemberId;

    @ApiModelProperty(example = "231")
    @ApiParam(value = "신고할 배달파티 id", required = true)
    @NotNull
    private int reportedDeliveryPartyId;

    @ApiModelProperty(example = "욕설로 신고합니다.")
    @ApiParam(value = "신고에 관한 주된 내용", required = true)
    @Size(min = 0, max = 100)
    private String reportContent;

    @ApiModelProperty(example = "true")
    @ApiParam(value = "멤버 차단 여부", required = true)
    @NotNull
    private boolean block;

    public DeliveryPartyReport toEntity(Member member, DeliveryParty deliveryParty, ReportCategory reportCategory, PostDeliveryPartyReportRegisterReq dto) {
        return DeliveryPartyReport.builder()
                .reportingMember(member)
                .reportedMember(deliveryParty.getChief())
                .reportCategory(reportCategory)
                .reportContent(dto.getReportContent())
                .deliveryParty(deliveryParty)
                .build();
    }
}
