package shop.geeksasang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.status.ValidStatus;

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
}