package shop.geeksasang.domain.report;

import lombok.Builder;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.status.ReportStatus;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.ReportCategory;

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

    public String additionalContent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="report_category_id")
    public ReportCategory reportCategory;

    @Enumerated(EnumType.STRING)
    public ReportStatus reportStatus;

    public Report(Member reportingMember, Member reportedMember, String reportContent, ReportCategory reportCategory, String additionalContent) {
        this.reportingMember = reportingMember;
        this.reportedMember = reportedMember;
        this.reportContent = reportContent;
        this.additionalContent = additionalContent;
        this.reportCategory = reportCategory;
        this.reportStatus = ReportStatus.ONGOING;
    }
}

/**
 * 게시글 신고가 3개 이상일 때는, 그 게시글 삭제
 *  사용자 신고가 1달 3개 이상일때는, 사용자가 게시물을 못올림
 * 1달마다 사용자 신고 카운트가 초기화가 됨
 * 사용자 차단하면 신고자에게 그 사용자가 쓴 게시물이 안보임
 * 사용자가 하루에 신고를 할 수 있는 갯수는 3개
 * 이미 신고를 한 게시물에는 다시 신고를 할 수 없음
 * 신고자가 같은 사용자를 여러번 신고할 수 없음
 * (옵션) 신고 카운트가 충족이 되어서 삭제, 차단되면 geeksasaeng@gmail.com로 해당 내용이 담긴 메일이 날라왔으면 좋겠음
 */