package shop.geeksasang.dto.member.post;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @ApiModelProperty(example = "geeksageek", value = "사용자 아이디", required = true)
    @Size(min = 6, max = 20)// validation: 최소길이 6자
    @Pattern(regexp="^(?=.*[a-zA-Z])[-a-zA-Z0-9_.]{6,20}$",
            message = "아이디는 6-20자의 영문과 숫자, 일부 특수문자(._-)만 입력 가능합니다.")
    private  String loginId;

    @ApiModelProperty(example = "2", value = "기숙사 id", required = true)
    @NotNull
    private int dormitoryId;

    @ApiModelProperty(example = "rlrtktod12!", value = "사용자 비밀번호")
    private  String password;

    @ApiModelProperty(example = "rlrtktod12!", value = "사용자 비밀번호 체크")
    private  String checkPassword;

}
