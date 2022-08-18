package shop.geeksasang.dto.member.patch;

import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
public class PatchMemberReq {

    @ApiModelProperty(example = "rlrtktod12!", value = "사용자 새로운 비밀번호", required = true)
//    @Size(min = 8, max = 15)
//    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$",
//            message = "비밀번호는 최소 8 자로 문자, 숫자 및 특수 문자를 최소 하나씩 포함해서 8-15자리 이내로 입력해주세요.")
    private String newPassword;

    @ApiModelProperty(example = "rlrtktod12!", value = "사용자 새로운 비밀번호 체크", required = true)
//    @Size(min = 8, max = 15)
//    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$",
//            message = "비밀번호는 최소 8 자로 문자, 숫자 및 특수 문자를 최소 하나씩 포함해서 8-15자리 이내로 입력해주세요.")
    private String checkNewPassword;

    @ApiModelProperty(example = "1", value = "기숙사 인덱스")
    @NotNull
    private Integer dormitoryId;

    @ApiModelProperty(example = "http://geeksasaeng.shop/s3/neo.jpg", value = "수정할 프로필 이미지 url")
    @NotBlank(message = "프로필 이미지 url 빈 값 허용 안함, 최소 1길이 이상")
    @Size(min=1)
    private String profileImgUrl;

    @ApiModelProperty(example = "긱사생", value = "사용자 닉네임", required = true)
    @Size(min = 3, max = 10)
    private  String nickname;

}
