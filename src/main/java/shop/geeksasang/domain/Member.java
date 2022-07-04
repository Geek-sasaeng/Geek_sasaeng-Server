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
    public void connectUniversity(University university){
        this.university = university;
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
                ", phoneValidKey='" + phoneValidKey + '\'' +
                ", profileImgUrl='" + profileImgUrl + '\'' +
                ", emailValidKey='" + emailValidKey + '\'' +
                ", emailValidStatus='" + emailValidStatus + '\'' +
                ", jwtToken='" + jwtToken + '\'' +
                '}';
    }
}
