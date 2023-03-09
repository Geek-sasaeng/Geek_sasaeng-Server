package shop.geeksasang.dto.s3;

import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class PostImageRes {

    @ApiModelProperty(example = "[" +
            "https://geeksasaeng-s3.s3.ap-northeast-2.amazonaws.com/%EA%B4%91%EA%B3%A0.png," +
            "https://geeksasaeng-s3.s3.ap-northeast-2.amazonaws.com/%EA%B4%91%EA%B3%A1.png]",
            value = "업로드한 이미지의 URL.")
    private List<String> imageUrls;

    public PostImageRes(List<String> imageUrl) {
        this.imageUrls = imageUrl;
    }
}
