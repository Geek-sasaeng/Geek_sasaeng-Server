package shop.geeksasang.domain;

import lombok.Getter;
import shop.geeksasang.config.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
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

}