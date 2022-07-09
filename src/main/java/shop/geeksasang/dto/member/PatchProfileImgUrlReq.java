package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor // 모든 파라미터 생성자
@NoArgsConstructor// 빈 생성자
@Data
public class PatchProfileImgUrlReq {

    @ApiModelProperty(value ="수정할 프로필 이미지 url",example = "www.site.image")
    @ApiParam(value = "수정할 프로필 이미지 url")
    @NotBlank(message = "프로필 이미지 url 빈 값 허용 안함, 최소 1길이 이상")
    @Size(min=1)
    private String profileImgUrl;
}
