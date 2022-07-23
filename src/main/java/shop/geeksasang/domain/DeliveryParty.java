package shop.geeksasang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.status.MatchingStatus;
import shop.geeksasang.config.type.OrderTimeCategoryType;
import shop.geeksasang.config.status.BaseStatus;
import shop.geeksasang.domain.report.DeliveryPartyReport;
import shop.geeksasang.dto.deliveryParty.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.PutDeliveryPartyReq;

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

    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus;

    @OneToMany(mappedBy = "deliveryParty")
    private List<DeliveryPartyReport> deliveryPartyReports;

    private String storeUrl;

    private int reportedCount;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="latitude",column = @Column(name="latitude")),
            @AttributeOverride(name="longitude", column = @Column(name="longitude"))
    })
    private Location location;

    public static DeliveryParty makeParty(PostDeliveryPartyReq dto, OrderTimeCategoryType orderTimeCategory, Dormitory dormitory, FoodCategory foodCategory, Member chief, List<HashTag> hashTagList) {
        DeliveryParty party = DeliveryParty.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .orderTime(dto.getOrderTime())
                .maxMatching(dto.getMaxMatching())
                .location(new Location(dto.getLatitude(),dto.getLongitude()))
                .chief(chief)
                .foodCategory(foodCategory)
                .orderTimeCategory(orderTimeCategory)
                .dormitory(dormitory)
                .deliveryPartyHashTags(new ArrayList<>())
                .matchingStatus(MatchingStatus.ONGOING)
                .currentMatching(1)
                .storeUrl(dto.getStoreUrl())
                .reportedCount(0)
                .build();

        party.setStatus(BaseStatus.ACTIVE);
        dormitory.addParty(party);
        for (HashTag hashTag : hashTagList) {
            DeliveryPartyHashTag deliveryPartyHashTag = new DeliveryPartyHashTag(party, hashTag);
            hashTag.addDeliveryPartyHashTag(deliveryPartyHashTag);
            party.deliveryPartyHashTags.add(deliveryPartyHashTag);
        }
        return party;
    }

    public DeliveryParty updateParty(PutDeliveryPartyReq dto, OrderTimeCategoryType orderTimeCategory, Dormitory dormitory, FoodCategory foodCategory, Member chief, List<HashTag> hashTagList){
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.orderTime = dto.getOrderTime();
        this.maxMatching = dto.getMaxMatching();
        this.location= new Location(dto.getLatitude(),dto.getLongitude());
        this.storeUrl = dto.getStoreUrl();
        this.chief = chief;
        this.foodCategory = foodCategory;
        this.orderTimeCategory = orderTimeCategory;
        this.dormitory = dormitory;
        this.setStatus(BaseStatus.ACTIVE);

        dormitory.addParty(this);
        for (HashTag hashTag : hashTagList) {
            DeliveryPartyHashTag deliveryPartyHashTag = new DeliveryPartyHashTag(this, hashTag);
            hashTag.addDeliveryPartyHashTag(deliveryPartyHashTag);
            this.deliveryPartyHashTags.add(deliveryPartyHashTag);
        }

        return this;
    }

    public void addReportedCount() {
        reportedCount++;
    }

    public void checkReportedCount() {
        if(reportedCount >= 3){
            setStatus(BaseStatus.INACTIVE);
        }
    }

    public void addReportedCountAndCheckReportedCount() {
        addReportedCount();
        checkReportedCount();
    }
}
