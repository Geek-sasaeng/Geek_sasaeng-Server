package shop.geeksasang.domain;

import lombok.Getter;

import javax.persistence.*;

import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.domain.MatchingStatus;

import java.time.LocalDateTime;

@Getter
@Entity
public class DeliveryParty extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="delivery_party_id")
    private int id;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member chief;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="domitory_id")
    private Domitory domitory;

    //나중에 수정 가능.
    @ManyToMany()
    private HashTag hashTag;

    @OneToOne(fetch=FetchType.LAZY)
    private Category category;

    private String title;

    private String content;

    private LocalDateTime orderTime;

    private int currentMatching;

    private int maxMatching;

    private String location;

    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus;

}
