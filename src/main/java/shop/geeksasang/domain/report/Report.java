package shop.geeksasang.domain.report;

import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.status.ReportStatus;
import shop.geeksasang.domain.member.Member;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "report_type")
@NoArgsConstructor
@Entity
public abstract class Report extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    public int id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(referencedColumnName = "member_id", name="reporting_member_id")
    public Member reportingMember;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(referencedColumnName = "member_id", name="reported_member_id")
    public Member reportedMember;

    public String reportContent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="report_category_id")
    public ReportCategory reportCategory;

    @Enumerated(EnumType.STRING)
    public ReportStatus reportStatus;

    public Report(Member reportingMember, Member reportedMember, String reportContent, ReportCategory reportCategory, String additionalContent) {
        this.reportingMember = reportingMember;
        this.reportedMember = reportedMember;
        this.reportContent = reportContent;
        this.reportCategory = reportCategory;
        this.reportStatus = ReportStatus.ONGOING;
    }
}
