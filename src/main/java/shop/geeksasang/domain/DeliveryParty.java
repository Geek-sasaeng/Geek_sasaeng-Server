package shop.geeksasang.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.domain.MatchingStatus;
import shop.geeksasang.config.domain.OrderTimeCategoryType;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @JoinColumn(name="dormitory_id")
    private Dormitory dormitory;

    //나중에 수정 가능.
    @OneToMany(mappedBy ="deliveryParty", targetEntity=DeliveryPartyHashTag.class, cascade = CascadeType.ALL)
    private List<HashTag> hashTags=new ArrayList<>();


    @OneToOne(fetch=FetchType.LAZY)
    @JsonIgnore
    private FoodCategory foodCategory;

    @OneToMany(mappedBy = "party")
    private List<DeliveryPartyMember> deliveryPartyMembers;

    private String title;

    private String content;

    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    private OrderTimeCategoryType orderTimeCategory;

    private int currentMatching;

    private int maxMatching;

    private String location;

    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus;

    //-// 연관 관계 편의 메서드 //-//

    public void connectChief(Member chief){
        this.chief = chief;
    }

    public void connectDormitory(Dormitory dormitory){
        this.dormitory = dormitory;
    }

    public void connectFoodCategory(FoodCategory foodCategory){
        this.foodCategory = foodCategory;
    }
}
