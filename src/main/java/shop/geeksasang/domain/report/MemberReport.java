package shop.geeksasang.domain.report;

import lombok.Builder;
import lombok.NoArgsConstructor;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.ReportCategory;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@DiscriminatorValue("member_report")
public  class MemberReport extends Report {

    @Builder
    public MemberReport(Member reportingMember, Member reportedMember, String reportContent, ReportCategory reportCategory, String additionalContent) {
        super(reportingMember, reportedMember, reportContent, reportCategory, additionalContent);
    }
}
