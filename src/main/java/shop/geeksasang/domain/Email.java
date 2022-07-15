package shop.geeksasang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.domain.ValidStatus;

import javax.persistence.*;

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

    private String address;

    @Enumerated(EnumType.STRING)
    private ValidStatus emailValidStatus;

    // 이메일 인증 성공
    public void changeEmailValidStatusToSuccess() {
        this.emailValidStatus = ValidStatus.SUCCESS;
    }
}