package shop.geeksasang.dto.member;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import shop.geeksasang.domain.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateMemberReq {

    @Size(min = 6)
    private  String loginId;

    @Size(min = 8)
    private  String password;

    @Size(min = 8)
    private  String checkPassword;

    @Size(min = 6)
    private  String nickname;

    @NotBlank
    private  String universityName;

    @Email
    private  String email;

    @Size(min = 10, max = 11)
    private  String phoneNumber;

    public Member toEntity() {
        return Member.builder()
                .loginId(getLoginId())
                .password(getPassword())
                .nickName(getNickname())
                .email(getEmail())
                .phoneNumber(getPhoneNumber())
                .build();
    }
}








