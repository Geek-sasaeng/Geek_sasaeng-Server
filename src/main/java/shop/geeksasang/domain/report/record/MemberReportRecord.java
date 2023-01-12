package shop.geeksasang.domain.report.record;

import lombok.NoArgsConstructor;
import shop.geeksasang.domain.member.Member;

import javax.persistence.*;

@Entity
@DiscriminatorValue("member_report_record")
@NoArgsConstructor
public class MemberReportRecord extends ReportRecord {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_member_id")
    private Member reportedMember;

    public MemberReportRecord(Member owner, Member reportedMember) {
        super(owner);
        this.reportedMember = reportedMember;
    }

    public boolean sameMember(Member reportedMember) {
        return this.reportedMember == reportedMember;
    }
}

//멤버가 멤버 중복 신고를 못하기 위해 만든 테이블