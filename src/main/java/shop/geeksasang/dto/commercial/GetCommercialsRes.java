package shop.geeksasang.dto.commercial;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import lombok.Getter;

import shop.geeksasang.domain.Commercial;

@Getter
public class GetCommercialsRes {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "광고 데이터 id.(pk).")
    private int id;

    @ApiModelProperty(example = "https://geeksasaeng-s3.s3.ap-northeast-2.amazonaws.com/%EA%B4%91%EA%B3%A0.png")
    @ApiParam(value = "광고 이미지의 URL.")
    private String imgUrl;

    public GetCommercialsRes(int id, String imgUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
    }

    public static GetCommercialsRes toDto(Commercial commercial){
        return new GetCommercialsRes(commercial.getId(), commercial.getImgUrl());
    }
}
