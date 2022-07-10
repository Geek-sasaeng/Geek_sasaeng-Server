package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Member;

@Getter
@Setter
@Builder
public class PatchPasswordRes {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "사용자 인덱스")
    private int id;

    @ApiModelProperty(example = "zero")
    @ApiParam(value = "사용자 로그인 아이디")
    private String loginId;

    @ApiModelProperty(example = "rlrtktod12!")
    @ApiParam(value = "사용자 새로운 비밀번호")
    private String newPassword;

    static public PatchPasswordRes toDto(Member member) {
        return PatchPasswordRes.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .newPassword(member.getPassword())
                .build();
    }
}
