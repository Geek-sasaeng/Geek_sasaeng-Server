package shop.geeksasang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.domain.Status;

import javax.persistence.*;

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

    private String loginId;

    private String nickName;

    private String email;

    private String password;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="university_id")
    private University university;

    private String phoneNumber;

    private String phoneValidKey;

    private String profileImgUrl;

    private String emailValidKey;

    private String emailValidStatus;

    private String jwtToken;

    public void changeStatusToActive(){
        super.setStatus(Status.ACTIVE);
    }

    //-// 연관 관계 편의 메서드 //-//
    // 대학교 정보 저장
    public void connectUniversity(University university){
        this.university = university;
    }

    // 수정: 폰번호 저장
    public void updatePhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    //수정: 폰 인증번호 수정
    public void updatePhoneValidKey(String phoneValidKey){
        this.phoneValidKey = phoneValidKey;
    }

    // 수정: 폰 번호
    public void updateProfileImgUrl(String profileImgUrl){
        this.profileImgUrl = profileImgUrl;
    }

    // 값 확인용 메서드
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
                ", phoneValidKey='" + phoneValidKey + '\'' +
                ", profileImgUrl='" + profileImgUrl + '\'' +
                ", emailValidKey='" + emailValidKey + '\'' +
                ", emailValidStatus='" + emailValidStatus + '\'' +
                ", jwtToken='" + jwtToken + '\'' +
                '}';
    }
}
