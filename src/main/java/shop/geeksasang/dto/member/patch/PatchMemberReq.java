package shop.geeksasang.dto.member.patch;

import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PatchMemberReq {

    @ApiModelProperty(example = "1", value = "기숙사 인덱스")
    private Integer dormitoryId;

    @ApiModelProperty(example = "http://geeksasaeng.shop/s3/neo.jpg", value = "수정할 프로필 이미지 url")
    private String profileImgUrl;

    @ApiModelProperty(example = "긱사생", value = "사용자 닉네임", required = true)
    private  String nickname;

}
