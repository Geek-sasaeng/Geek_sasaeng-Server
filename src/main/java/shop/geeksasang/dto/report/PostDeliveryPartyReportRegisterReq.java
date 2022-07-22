package shop.geeksasang.dto.report;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.ReportCategory;
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

    @ApiModelProperty(example = "부가적인 신고 내용입니다.")
    @ApiParam(value = "신고에 관한 부가적인 내용", required = true)
    @Size(min = 0, max = 100)
    private String additionalContent;

    @ApiModelProperty(example = "true")
    @ApiParam(value = "멤버 차단 여부", required = true)
    @NotNull
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
