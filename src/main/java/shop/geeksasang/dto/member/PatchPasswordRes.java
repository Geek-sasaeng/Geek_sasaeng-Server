package shop.geeksasang.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Member;

@Getter
@Setter
@Builder
public class PatchPasswordRes {

    private int id;
    private String loginId;
    private String newPassword;

    static public PatchPasswordRes toDto(Member member) {
        return PatchPasswordRes.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .newPassword(member.getPassword())
                .build();
    }
}
