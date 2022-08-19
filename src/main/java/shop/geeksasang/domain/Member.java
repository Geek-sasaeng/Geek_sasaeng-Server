package shop.geeksasang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;
import shop.geeksasang.config.domain.*;
import shop.geeksasang.config.status.LoginStatus;
import shop.geeksasang.config.status.BaseStatus;
import shop.geeksasang.config.type.MemberLoginType;
import shop.geeksasang.domain.report.MemberReport;
import shop.geeksasang.domain.report.record.DeliverPartyReportRecord;
import shop.geeksasang.domain.report.record.MemberReportRecord;
import shop.geeksasang.utils.encrypt.SHA256;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate //변경된 것만 바꿔준다.
@Entity
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private int id;

    private String loginId;

    private String nickName;

    private String password;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="university_id")
    private University university;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="phoneNumber_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private PhoneNumber phoneNumber;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="email_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Email email;

    private String profileImgUrl;

    private String jwtToken;

    private String informationAgreeStatus; // 회원 정보 동의 여부

    @Enumerated(EnumType.STRING)
    private LoginStatus loginStatus; // 첫 번째 로그인인지 아닌지

    @Enumerated(EnumType.STRING)
    private MemberLoginType memberLoginType;

    @OneToMany(mappedBy = "reportingMember")
    private List<MemberReport> reportingMembers;

    @OneToMany(mappedBy = "reportedMember")
    private List<MemberReport> reportedMembers;

    //만약 신고가 계속 늘어나면 필드도 늘어야 하는데 추상화로 묶을까?
    //대신 검증할 때 강제 캐스팅을 해야 하는데 이것도 좋지 않아 보인다.
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<MemberReportRecord> memberReportRecords;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<DeliverPartyReportRecord> deliverPartyReportRecords;

    private int perDayReportingCount;

    private int reportedCount;

    @OneToMany(mappedBy = "blockingMember")
    List<Block> blocks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dormitory_id")
    private Dormitory dormitory;

    private String fcmToken; // 파이어베이스 FCM 토큰

    //-// 연관 관계 편의 메서드 //-//
    public void changeStatusToActive(){
        super.setStatus(BaseStatus.ACTIVE);
    }// status ACTIVE로 수정

    // 대학교 정보 저장
    public void connectUniversity(University university){
        this.university = university;
    }

    // 수정: 회원정보 동의 수정
    public void updateInformationAgreeStatus(String informationAgreeStatus){
        this.informationAgreeStatus = informationAgreeStatus;
    }

    // 회원 탈퇴
    public void changeStatusToInactive(){
        super.setStatus(BaseStatus.INACTIVE);
    }

    // 로그인 안해본 디폴트 저장
    public void changeLoginStatusToNever(){
        this.loginStatus = LoginStatus.NEVER;
    }

    // 로그인 횟수 상태 첫번째 초과 저장
    public void changeLoginStatusToNotNever(){
        this.loginStatus = LoginStatus.NOTNEVER;
    }

    public boolean containReportedMemberRecord(Member reportedMember) {
        for (MemberReportRecord memberReportRecord : memberReportRecords) {
            if(memberReportRecord.sameMember(reportedMember)){
                return true;
            }
        }
        return false;
    }

    public boolean containReportedDeliveryPartyRecord(DeliveryParty reportedDeliveryParty) {
        for (DeliverPartyReportRecord deliverPartyReportRecord : deliverPartyReportRecords) {
            if(deliverPartyReportRecord.sameParty(reportedDeliveryParty)){
                return true;
            }
        }
        return false;
    }

    public void addReportedCountAndCheckReportedCount() {
        reportedCount++;
        if(checkReportedCount()){
            setStatus(BaseStatus.REPORTED); //신고 상태를 추가
        }
    }

    public boolean checkReportedCount(){
        return reportedCount >= 3;
    }

    public void addMemberReportRecord(Member reportedMember) {
        addOneDayReportCount();
        memberReportRecords.add(new MemberReportRecord(this, reportedMember));
    }

    public void addDeliveryPartyReportRecord(DeliveryParty deliveryParty) {
        addOneDayReportCount();
        deliverPartyReportRecords.add(new DeliverPartyReportRecord(this, deliveryParty));
    }

    public void addOneDayReportCount(){
        perDayReportingCount++;
    }

    public boolean checkPerDayReportCount() {
        return perDayReportingCount >= 3;
    }

    public void updateFcmToken(String fcmToken){
        this.fcmToken = fcmToken;
    }

    public void changeProfileImgUrl(String profileImgUrl){
        this.profileImgUrl = profileImgUrl;
    }

    public Member update(Dormitory dormitory, String profileImgUrl, String newPassword, String newNickName) {
        this.dormitory = dormitory;
        this.profileImgUrl = profileImgUrl;
        this.nickName = newNickName;

        if(StringUtils.hasText(newPassword)){
            this.password = SHA256.encrypt(newPassword);
        }

        return this;
    }

    //테스트용
    public Member(int id, String nickName) {
        this.id = id;
        this.nickName = nickName;
        super.setStatus(BaseStatus.ACTIVE);
    }

    public Member(String nickName) {
        this.id = id;
        this.nickName = nickName;
        super.setStatus(BaseStatus.ACTIVE);
    }
}
