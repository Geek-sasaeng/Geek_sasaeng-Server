package shop.geeksasang.dto.login;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.status.LoginStatus;
import shop.geeksasang.config.type.MemberLoginType;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtResponse {
    @ApiModelProperty(example = "geeksasaeng")
    @ApiParam(value = "사용자 ID")
    private String loginId;

    @ApiModelProperty(example = "긱사생")
    @ApiParam(value = "사용자 닉네임")
    private String nickname;

    @ApiModelProperty(example = "https://geeksasaeng-s3.s3.ap-northeast-2.amazonaws.com/%EA%B4%91%EA%B3%A0.png")
    @ApiParam(value = "사용자 프로필 사진")
    private String profileImgUrl;

    @ApiModelProperty(example = "가천대학교")
    @ApiParam(value = "사용자 대학교")
    private String universityName;

    @ApiModelProperty(example = "forceTlight@gmail.com")
    @ApiParam(value = "사용자 대학교 이메일")
    private String email;

    @ApiModelProperty(example = "01012341234")
    @ApiParam(value = "사용자 핸드폰 번호")
    private String phoneNumber;

    @ApiModelProperty(example = "NAVER_USER")
    @ApiParam(value = "사용자 로그인 유형, 네이버 = NAVER_USER / 일반 = NORMAL_USER")
    private MemberLoginType memberLoginType;

    @ApiModelProperty(example = "NEVER")
    @ApiParam(value = "로그인 한 적이 있는지, 없으면 = NEVER / 있으면 = NOTNEVER")
    private LoginStatus loginStatus;

    @ApiModelProperty(example = "1")
    @ApiParam(value = "기숙사 인덱스")
    private int dormitoryId;

    @ApiModelProperty(example = "1기숙사")
    @ApiParam(value = "사용자 기숙사 이름")
    private String dormitoryName;
}
