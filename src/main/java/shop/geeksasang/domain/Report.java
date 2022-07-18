package shop.geeksasang.domain;

import shop.geeksasang.config.domain.BaseEntity;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public class Report extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "report_id")
    private int id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Member reportingMember;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Member reportedMember;

    private String content;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="delivery_party_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
//    private DeliveryParty deliveryParty;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="report_category_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private ReportCategory reportCategory;

//    @Enumerated
//    private ReportStatus reportStatus;

}
