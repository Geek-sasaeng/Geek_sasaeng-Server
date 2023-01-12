package shop.geeksasang.dto.report;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.domain.report.ReportCategory;
import shop.geeksasang.domain.report.MemberReport;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class PostMemberReportRegisterReq {

    @ApiModelProperty(example = "9")
    @ApiParam(value = "신고 카테고리 id, 0 ~ 10 까지의 int 값.", required = true)
    @Range(min = 0, max = 10)
    private int reportCategoryId;

    @ApiModelProperty(example = "34")
    @ApiParam(value = "신고하고, 차단할 멤버 id", required = true)
    @NotNull
    private int reportedMemberId;

    @ApiModelProperty(example = "욕설로 신고합니다.")
    @ApiParam(value = "신고에 관한 주된 내용", required = true)
    @Size(min = 0, max = 100)
    private String reportContent;

    @ApiModelProperty(example = "true")
    @ApiParam(value = "멤버 차단 여부", required = true)
    @NotNull
    private boolean block;

    public MemberReport toEntity(Member member, Member reportedMember, PostMemberReportRegisterReq dto, ReportCategory reportCategory) {
        return MemberReport.builder()
                .reportingMember(member)
                .reportedMember(reportedMember)
                .reportContent(dto.getReportContent())
                .reportCategory(reportCategory)
                .build();
    }
}
