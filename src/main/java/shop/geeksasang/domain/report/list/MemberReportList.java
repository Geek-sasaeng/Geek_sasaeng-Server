package shop.geeksasang.domain.report.list;

import shop.geeksasang.domain.Member;

import javax.persistence.*;

@Entity
@DiscriminatorValue("member_report_list")
public class MemberReportList extends ReportList {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_member_id")
    private Member reportedMembers;
}

//멤버가 멤버 중복 신고를 못하기 위해 만든 테이블