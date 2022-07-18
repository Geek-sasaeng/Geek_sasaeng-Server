package shop.geeksasang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.*;
import shop.geeksasang.config.status.LoginStatus;
import shop.geeksasang.config.status.BaseStatus;
import shop.geeksasang.config.type.MemberLoginType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private int id;

    @NotNull
    private String loginId;

    @NotNull
    private String nickName;

    @NotNull
    private String password;

    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="university_id")
    private University university;

    @NotNull
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="phoneNumber_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private PhoneNumber phoneNumber;

    @NotNull
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="email_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Email email;

    private String profileImgUrl;

    private String jwtToken;

    @NotNull
    private String informationAgreeStatus; // 회원 정보 동의 여부

    @NotNull
    @Enumerated(EnumType.STRING)
    private LoginStatus loginStatus; // 첫 번째 로그인인지 아닌지

    @NotNull
    @Enumerated(EnumType.STRING)
    private MemberLoginType memberLoginType;

    public void changeStatusToActive(){
        super.setStatus(BaseStatus.ACTIVE);
    }

    //-// 연관 관계 편의 메서드 //-//
    // 대학교 정보 저장
    public void connectUniversity(University university){
        this.university = university;
    }

    // 수정: 회원정보 동의 수정
    public void updateInformationAgreeStatus(String informationAgreeStatus){
        this.informationAgreeStatus = informationAgreeStatus;
    }

    // 수정: 프로필 이미지
    public void updateProfileImgUrl(String profileImgUrl){
        this.profileImgUrl = profileImgUrl;
    }

    // 값 확인용 메서드
    public void updateNickname(String nickName) { this.nickName = nickName; }

    public void updatePassword(String password) { this.password = password; }

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

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", loginId='" + loginId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", university=" + university +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profileImgUrl='" + profileImgUrl + '\'' +
                ", jwtToken='" + jwtToken + '\'' +
                '}';
    }
}
