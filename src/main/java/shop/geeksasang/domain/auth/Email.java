package shop.geeksasang.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.status.BaseStatus;
import shop.geeksasang.config.status.ValidStatus;
import shop.geeksasang.domain.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
public class Email extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private int id;

    @OneToOne(mappedBy = "email")
    private Member member;

    @NotNull
    private String address;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ValidStatus emailValidStatus;

    // 이메일 인증 성공
    public void changeEmailValidStatusToSuccess() {
        this.emailValidStatus = ValidStatus.SUCCESS;
    }


    public Email(String address) {
        this.address = address;
        this.setStatus(BaseStatus.ACTIVE);
        this.emailValidStatus = ValidStatus.SUCCESS;
    }

    //테스트용
    public Email(String address, ValidStatus validStatus){
        this.address = address;
        this.emailValidStatus = validStatus;
    }

    public void delete() {
        setStatus(BaseStatus.INACTIVE);
    }
}