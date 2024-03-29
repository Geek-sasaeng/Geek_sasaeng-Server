package shop.geeksasang.dto.login.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.config.status.LoginStatus;
import shop.geeksasang.utils.login.LoginDtoFilter;

import javax.validation.constraints.NotBlank;


@Getter @Setter
@Builder
@JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LoginDtoFilter.class)
public class PostLoginRes {
    @ApiModelProperty(example = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJqd3RJbmZvIjp7InVuaXZlcnNpdHlJZCI6MSwidXNlcklkIjoxN30sImlhdCI6MTY1NzQ1MTU1NiwiZXhwIjoxNjU4MzQwNTg5fQ.0H1fUvms49VVcH9gkKD5P3PVP8X73mfX_r8Y14qH598",value = "jwt Token")
    private String jwt;

    @ApiModelProperty(example = "neoneo",value = "로그인한 멤버 닉네임" )
    private String nickName;

    @ApiModelProperty(example = "NEVER",value = "로그인 횟수 상태")
    private LoginStatus loginStatus;

    @ApiModelProperty(example = "1기숙사",value = "로그인 한 멤버의 기숙사 이름 / loginStatus가 NOTNEVER일 때만 반환하는 값입니다.")
    private String dormitoryName;

    @ApiModelProperty(example="1",value = "로그인 한 멤버의 기숙사 id / loginStatus가 NOTNEVER일 때만 반환하는 값입니다.")
    private Integer dormitoryId;

    @ApiModelProperty(example="https://geeksasaeng-s3.s3.ap-northeast-2.amazonaws.com/%EA%B4%91%EA%B3%A0.png",value = "유저 프로필 이미지 주소" )
    private String profileImgUrl;

    @ApiModelProperty(example = "c1aTnbZPaWP0_KZbcSuh_h:APA91bFG4r3Z6-dw88UQFikVxkNApaUeq508RNeYxgmrj7miyGdSIy6YXMLJj3jAmCZERNqSLgLm4gn3fyhnBn2iUsra_EriXMqsOlXkyUf9ugNAKMPiKZGNYbn6gKLhMYWB3xTJoRVk",value = "FCM 토큰값" )
    @NotBlank
    private String fcmToken;

    @ApiModelProperty(example="1", value = "사용자 memberId")
    private int memberId;
}
