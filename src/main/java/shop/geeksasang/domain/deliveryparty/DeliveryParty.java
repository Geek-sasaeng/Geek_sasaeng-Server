package shop.geeksasang.domain.deliveryparty;

import lombok.*;

import javax.persistence.*;

import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.config.status.MatchingStatus;
import shop.geeksasang.config.status.OrderStatus;
import shop.geeksasang.config.type.OrderTimeCategoryType;
import shop.geeksasang.config.status.BaseStatus;
import shop.geeksasang.domain.location.Location;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.domain.report.DeliveryPartyReport;
import shop.geeksasang.domain.university.Dormitory;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.put.PutDeliveryPartyReq;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

    @OneToMany(mappedBy ="deliveryParty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryPartyHashTag> deliveryPartyHashTags = new ArrayList<>();

    @OneToOne(fetch=FetchType.LAZY)
    private FoodCategory foodCategory;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
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

    private String bank;

    private String accountNumber;

    private String chatRoomName;

    private String uuid;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

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
                .bank(dto.getBank())
                .accountNumber(dto.getAccountNumber())
                .chatRoomName(dto.getChatRoomName())
                .chief(chief)
                .foodCategory(foodCategory)
                .orderTimeCategory(orderTimeCategory)
                .dormitory(dormitory)
                .deliveryPartyHashTags(new ArrayList<>())
                .matchingStatus(MatchingStatus.ONGOING)
                .currentMatching(1)
                .storeUrl(dto.getStoreUrl())
                .reportedCount(0)
                .uuid(createUuid())
                .deliveryPartyMembers(new ArrayList<>())
                .orderStatus(OrderStatus.BEFORE_ORDER)
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

        if(hashTagList.isEmpty()){
            deliveryPartyHashTags.clear();
        }

        for (HashTag hashTag : hashTagList) {
            DeliveryPartyHashTag deliveryPartyHashTag = new DeliveryPartyHashTag(this, hashTag);
            hashTag.addDeliveryPartyHashTag(deliveryPartyHashTag);
            this.deliveryPartyHashTags.add(deliveryPartyHashTag);
        }

        return this;
    }

    public void addReportedCountAndCheckReportedCount() {
        addReportedCount();
        checkReportedCount();
    }

    public void addReportedCount() {
        reportedCount++;
    }

    public void checkReportedCount() {
        if(reportedCount >= 3){
            setStatus(BaseStatus.INACTIVE);
        }
    }

    public static String createUuid(){
        return UUID.randomUUID().toString();
    }

    public void addPartyMember(DeliveryPartyMember partyMember){
        this.deliveryPartyMembers.add(partyMember);
    }


    // 배달파티 삭제
    private void changeStatusToInactive(){
        super.setStatus(BaseStatus.INACTIVE);
    }

    public void addCurrentMatching(){
        currentMatching++;
    }

    public void changeMatchingStatusToFinish() {
        this.matchingStatus = MatchingStatus.FINISH;
    }

    public void changeMatchingStatusToOngoing(){
        this.matchingStatus = MatchingStatus.ONGOING;
    }

    public boolean isNotChief(Member attemptedChief){
        return attemptedChief != chief;
    }

    public boolean isNotChief(DeliveryPartyMember deliveryPartyMember){
        return deliveryPartyMember.getParticipant().getId() != chief.getId();
    }

    public boolean isChief(Member member){
        return this.chief == member;
    }

    public boolean memberIsOnlyChief() {
        return deliveryPartyMembers.size() == 1;
    }

    public void changeOrderStatusToOrderComplete(){
        this.orderStatus = OrderStatus.ORDER_COMPLETE;
    }

    public void changeOrderStatusToDeliveryComplete(){
        this.orderStatus = OrderStatus.DELIVERY_COMPLETE;
    }

    public boolean isNotDeleteMember(DeliveryPartyMember deliveryPartyMember) {
        DeliveryPartyMember member = findDeliveryPartyMember(deliveryPartyMember);
        return member.isActive();
    }

    public DeliveryParty deleteParty() {
        minusMatching();
        deleteNowChiefInParticipants();
        deleteParticipants();
        changeStatusToInactive();
        chief = null;
        return this;
    }

    private void deleteParticipants() {
        for( DeliveryPartyMember deliveryPartyMember : deliveryPartyMembers ){
            deliveryPartyMember.leaveDeliveryParty();
        }
    }

    public DeliveryParty leaveNowChiefAndChangeChief() {
        minusMatching();
        deleteNowChiefInParticipants();
        DeliveryPartyMember candidateChief = findCandidateChief();
        chief = candidateChief.getParticipant();
        return this;
    }

    private DeliveryPartyMember findCandidateChief() {
        return deliveryPartyMembers.stream()
                .filter(DeliveryPartyMember::isActive)
                .findFirst()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.EMPTY_DELIVERY_PARTY));
    }

    //관계를 끊어도 연관관계 처리를 애매하게 해서 데이터가 계속 남아있었다. 확실하게 다 지워버리자.
    private void deleteNowChiefInParticipants(){
        DeliveryPartyMember removedChief = findDeliveryPartyInChief();
        removedChief.leaveDeliveryParty();
    }

    public void removeDeliveryPartyMember(DeliveryPartyMember deliveryPartyMember){
        //deliveryPartyMembers.remove(deliveryPartyMember); //이걸 상태를 바꿔야함. 하드딜리트가 아닌 소프트 딜리트로
        partyMemberStatusChangeToInActive(deliveryPartyMember);
        minusMatching();

        //참여 인원이 0명이면 파티 삭제
        if(currentMatching <= 0){
            changeStatusToInactive();
        }

        // 현재인원 == (최대인원-1) 이면 MatchingStatus를 FINISH -> ONGOING으로 수정
        if(currentMatching < maxMatching){
            changeMatchingStatusToOngoing();
        }
    }

    private void partyMemberStatusChangeToInActive(DeliveryPartyMember deliveryPartyMember) {
        DeliveryPartyMember removeMember = findDeliveryPartyMember(deliveryPartyMember);
        removeMember.leaveDeliveryParty();
    }

    private DeliveryPartyMember findDeliveryPartyMember(DeliveryPartyMember deliveryPartyMember) {
        return deliveryPartyMembers
                .stream()
                .filter(member -> member == deliveryPartyMember)
                .findFirst()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));
    }

    private DeliveryPartyMember findDeliveryPartyInChief() {
        return deliveryPartyMembers
                .stream()
                .filter(member -> member.getParticipant() == chief)
                .findFirst()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));
    }


    private void minusMatching(){
        currentMatching--;
    }

    private DeliveryPartyMember findFirstActiveDeliveryPartyMember(){
        return deliveryPartyMembers.stream()
                .findFirst()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));
    }


    public long getDeliveryPartyMemberSize() {
        return deliveryPartyMembers.stream()
                .filter(DeliveryPartyMember::isActive)
                .count();
    }
}
