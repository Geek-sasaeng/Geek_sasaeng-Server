package shop.geeksasang.domain;

import shop.geeksasang.config.domain.BaseEntity;

import javax.persistence.*;

@Entity
public class ReportCategory extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "report_category_id")
    private int reportCategoryId;

    private String title;
}
