package shop.geeksasang.dto.member.post;

import io.swagger.annotations.ApiModelProperty;

import lombok.Builder;
import lombok.Getter;

import shop.geeksasang.domain.member.Member;

@Getter
public class PostMemberInfoRes {

    @ApiModelProperty(example = "1", value = "수정된 기숙사 인덱스")
    private int dormitoryId;

    @ApiModelProperty(example = "1", value = "수정된 기숙사 인덱스")
    private String dormitoryName;

    @ApiModelProperty(example = "https://geeksasaeng-s3.s3.ap-northeast-2.amazonaws.com/%EA%B4%91%EA%B3%A0.png", value = "수정된 프로필 이미지 url")
    private String profileImgUrl;

    @ApiModelProperty(example = "긱사생", value = "사용자 닉네임")
    private  String nickname;

    @ApiModelProperty(example = "nsfasdsdf21", value = "사용자 id")
    private  String loginId;

    public static PostMemberInfoRes toDto(Member updateMember){
        return PostMemberInfoRes.builder()
                .dormitoryId(updateMember.getDormitory().getId())
                .dormitoryName(updateMember.getDormitory().getName())
                .profileImgUrl(updateMember.getProfileImgUrl())
                .nickname(updateMember.getNickName())
                .loginId(updateMember.getLoginId())
                .build();
    }

    @Builder
    public PostMemberInfoRes(int dormitoryId, String dormitoryName, String profileImgUrl, String nickname, String loginId) {
        this.dormitoryId = dormitoryId;
        this.dormitoryName = dormitoryName;
        this.profileImgUrl = profileImgUrl;
        this.nickname = nickname;
        this.loginId = loginId;
    }
}
