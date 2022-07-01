package shop.geeksasang.dto;

import lombok.*;
import shop.geeksasang.domain.Member;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateMemberReq {
    private  String loginId;
    private  String password;
    private  String checkPassword;
    private  String nickname;
    private  String universityName;
    private  String email;
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








