package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Member;

@Getter @Setter
@Builder
public class PatchNicknameRes {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "사용자 인덱스")
    private int id;

    @ApiModelProperty(example = "zero")
    @ApiParam(value = "사용자 로그인 아이디")
    private String loginId;

    @ApiModelProperty(example = "긱사생1")
    @ApiParam(value = "사용자 닉네임")
    private String nickname;

    static public PatchNicknameRes toDto(Member member) {
        return PatchNicknameRes.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .nickname(member.getNickName())
                .build();
    }
}
