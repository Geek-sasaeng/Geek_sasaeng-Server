package shop.geeksasang.dto.member.patch;

import io.swagger.annotations.ApiModelProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import shop.geeksasang.domain.Member;

@Getter
public class PatchMemberRes {

    @ApiModelProperty(example = "1", value = "기숙사 인덱스")
    private int dormitoryId;

    @ApiModelProperty(example = "1", value = "기숙사 인덱스")
    private String dormitoryName;

    @ApiModelProperty(example = "http://geeksasaeng.shop/s3/neo.jpg", value = "수정할 프로필 이미지 url")
    private String profileImgUrl;

    @ApiModelProperty(example = "긱사생", value = "사용자 닉네임", required = true)
    private  String nickname;

    public static PatchMemberRes toDto(Member updateMember){
        return PatchMemberRes.builder()
                .dormitoryId(updateMember.getDormitory().getId())
                .dormitoryName(updateMember.getDormitory().getName())
                .profileImgUrl(updateMember.getProfileImgUrl())
                .nickname(updateMember.getNickName())
                .build();
    }

    @Builder
    public PatchMemberRes(int dormitoryId, String dormitoryName, String profileImgUrl, String nickname) {
        this.dormitoryId = dormitoryId;
        this.dormitoryName = dormitoryName;
        this.profileImgUrl = profileImgUrl;
        this.nickname = nickname;
    }
}
