package shop.geeksasang.dto.s3;

import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;

@Getter
public class PostImageRes {

    @ApiModelProperty(example = "https://geeksasaeng-s3.s3.ap-northeast-2.amazonaws.com/%EA%B4%91%EA%B3%A0.png", value = "업로드한 이미지의 URL.")
    private String imageUrl;

    public PostImageRes(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
