package shop.geeksasang.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Member;

@Getter @Setter
@Builder
public class PatchNicknameRes {

    private int id;
    private String loginId;
    private String nickname;

    static public PatchNicknameRes toDto(Member member) {
        return PatchNicknameRes.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .nickname(member.getNickName())
                .build();
    }
}
