package shop.geeksasang.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Member;

@Getter @Setter
@Builder
public class CreateMemberRes {
    private  String loginId;
    private  String nickname;
    private  String universityName;
    private  String email;
    private  String phoneNumber;

    static public CreateMemberRes toDto(Member member) {
        return CreateMemberRes.builder()
                .loginId(member.getLoginId())
                .nickname(member.getNickName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
