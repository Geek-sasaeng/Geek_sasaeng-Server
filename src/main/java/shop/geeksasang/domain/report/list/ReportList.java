package shop.geeksasang.domain.report.list;


import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.domain.Member;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "report_list_type")
@Entity
public abstract class ReportList extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_list_id")
    public int id;

    @OneToOne(fetch =  FetchType.LAZY, mappedBy = "reportList")
    public Member owner;
}
