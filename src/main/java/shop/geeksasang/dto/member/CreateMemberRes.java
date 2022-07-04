package shop.geeksasang.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Member;

import javax.validation.constraints.Email;

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
                .universityName(member.getUniversity().getName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
