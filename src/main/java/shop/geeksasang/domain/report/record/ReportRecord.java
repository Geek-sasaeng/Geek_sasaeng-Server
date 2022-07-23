package shop.geeksasang.domain.report.record;


import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.domain.Member;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "report_record_type")
@NoArgsConstructor
@Entity
public abstract class ReportRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_list_id")
    protected int id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    protected Member owner;

    public ReportRecord(Member owner) {
        this.owner = owner;
    }
}
