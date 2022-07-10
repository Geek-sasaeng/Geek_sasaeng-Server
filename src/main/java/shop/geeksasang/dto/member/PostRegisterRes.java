package shop.geeksasang.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Member;

@Getter @Setter
@Builder
public class PostRegisterRes {

    private  String loginId;
    private  String nickname;
    private  String universityName;
    private  String email;
    private  String phoneNumber;

    static public PostRegisterRes toDto(Member member) {
        return PostRegisterRes.builder()
                .loginId(member.getLoginId())
                .nickname(member.getNickName())
                .universityName(member.getUniversity().getName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
