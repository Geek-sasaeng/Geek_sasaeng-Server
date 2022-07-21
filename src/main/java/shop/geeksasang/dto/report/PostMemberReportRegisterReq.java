package shop.geeksasang.dto.report;

import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.ReportCategory;
import shop.geeksasang.domain.report.MemberReport;

@Getter @Setter
public class PostMemberReportRegisterReq {
    private int reportCategoryId;
    private int reportedMemberId;
    private String reportContent;
    private String additionalContent;
    private boolean isBlock;

    public MemberReport toEntity(Member member, Member reportedMember, PostMemberReportRegisterReq dto, ReportCategory reportCategory) {
        return MemberReport.builder()
                .reportingMember(member)
                .reportedMember(reportedMember)
                .reportContent(dto.getReportContent())
                .additionalContent(dto.getAdditionalContent())
                .reportCategory(reportCategory)
                .build();
    }
}
