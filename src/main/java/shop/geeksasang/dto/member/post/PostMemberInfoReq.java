package shop.geeksasang.dto.member.post;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Data
@NoArgsConstructor
public class PostMemberInfoReq {

    @ApiModelProperty(example = "https://geeksasaeng-s3.s3.ap-northeast-2.amazonaws.com/%EA%B4%91%EA%B3%A0.png", value = "프로필 이미지")
    private MultipartFile profileImg;

    @ApiModelProperty(example = "긱사생닉넴", value = "사용자 닉네임", required = true)
    @Size(min = 3, max = 10)
    private  String nickname;

    @ApiModelProperty(example = "2", value = "기숙사 id", required = true)
    @NotNull
    private int dormitoryId;


}
