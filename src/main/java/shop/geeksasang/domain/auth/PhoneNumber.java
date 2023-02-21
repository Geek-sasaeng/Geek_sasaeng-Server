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
public class PhoneNumber extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="phoneNumber_id")
    private int id;

    @OneToOne(mappedBy = "phoneNumber")
    private Member member;

    @NotNull
    private String number;

    @Enumerated(EnumType.STRING)
    private ValidStatus phoneValidStatus;

    //-// 연관 관계 편의 메서드 //-//
    // 핸드폰 인증 성공
    public void changePhoneValidStatusToSuccess() {
        this.phoneValidStatus = ValidStatus.SUCCESS;
    }

    //테스트용
    public PhoneNumber(String number,ValidStatus phoneValidStatus){
        this.number = number;
        this.phoneValidStatus = phoneValidStatus;
    };

    public void delete() {
        setStatus(BaseStatus.INACTIVE);
    }
}
