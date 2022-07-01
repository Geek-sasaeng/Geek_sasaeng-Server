package shop.geeksasang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.domain.MatchingStatus;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class DeliveryParty extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private List<HashTag> hashTag;
//    @ManyToOne()
//    @JoinColumn(name="hashtag_id")
//    private HashTag hashTag;

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


    //-// 연관 관계 편의 메서드 //-//

    public void connectChief(Member chief){
        this.chief = chief;
        //university.getMemberList().add(this); TODO:##파티장 연결하고 이거 해야하나??
    }

    public void connectDomitory(Domitory domitory){
        this.domitory = domitory;
    }

    public void connectHashTag(){// TODO:해시태그 리스트 받아와서 저장
        this.hashTag = null;
    }

    public void connectCategory(Category category){
        this.category = category;
    }
}
