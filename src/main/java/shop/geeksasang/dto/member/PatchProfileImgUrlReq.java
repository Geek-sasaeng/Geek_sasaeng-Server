package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor // 모든 파라미터 생성자
@NoArgsConstructor// 빈 생성자
@Data
public class PatchProfileImgUrlReq {

    @ApiModelProperty(value ="프로필 이미지 url 입력. 빈 값 허용 안함, 최소 1길이 이상")
    @NotNull // null 값 허용 안함
    @Size(min=1)
    private String profileImgUrl;
}
