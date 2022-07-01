package shop.geeksasang.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import shop.geeksasang.config.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue
    private int memberIdx;

    private String loginId;

    private String nickName;
    private String email;
    private String password;
    private University university;
    private String phoneNumber;
    private String phoneValidKey;
    private String profileImgUrl;
    private String emailValidKey;
    private String emailValidStatus;
    private String jwtToken;
}
