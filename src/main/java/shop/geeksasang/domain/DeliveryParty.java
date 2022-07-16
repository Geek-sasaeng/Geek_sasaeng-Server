package shop.geeksasang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.domain.MatchingStatus;
import shop.geeksasang.config.domain.OrderTimeCategoryType;
import shop.geeksasang.config.domain.Status;
import shop.geeksasang.dto.deliveryParty.PostDeliveryPartyReq;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy ="deliveryParty", cascade = CascadeType.ALL)
    private List<DeliveryPartyHashTag> deliveryPartyHashTags = new ArrayList<>();

    @OneToOne(fetch=FetchType.LAZY)
    private FoodCategory foodCategory;

    @OneToMany(mappedBy = "party")
    private List<DeliveryPartyMember> deliveryPartyMembers = new ArrayList<>();

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

    public static DeliveryParty makeParty(PostDeliveryPartyReq dto, OrderTimeCategoryType orderTimeCategory, Dormitory dormitory, FoodCategory foodCategory, Member chief, List<HashTag> hashTagList) {
        DeliveryParty party = DeliveryParty.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .orderTime(dto.getOrderTime())
                .maxMatching(dto.getMaxMatching())
                .location(dto.getLocation())
                .chief(chief)
                .foodCategory(foodCategory)
                .orderTimeCategory(orderTimeCategory)
                .dormitory(dormitory)
                .deliveryPartyHashTags(new ArrayList<>())
                .matchingStatus(MatchingStatus.ONGOING)
                .currentMatching(1)
                .build();
        party.setStatus(Status.ACTIVE);
        System.out.println("party.deliveryPartyHashTags = " + party.deliveryPartyHashTags);
        for (HashTag hashTag : hashTagList) {
            party.deliveryPartyHashTags.add(new DeliveryPartyHashTag(party, hashTag));
        }
        return party;
    }
}
