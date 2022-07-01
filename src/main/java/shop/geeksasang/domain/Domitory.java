package shop.geeksasang.domain;

import lombok.Getter;

import javax.persistence.*;

import shop.geeksasang.config.domain.BaseEntity;

@Getter
@Entity
public class Domitory extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="domitory_id")
    private int id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="university_id")
    private University university;

    private String name;
}