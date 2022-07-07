package shop.geeksasang.dto.email;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/*
    [이메일 인증]
    Body: 이메일 주소, 학교 이름
 */
@Data
public class EmailReq {
    @Email
    @NotBlank(message = "이메일은 필수로 입력해야합니다 ")
    @ApiModelProperty(value = "대학교 이메일")
    private String email;

    @ApiModelProperty(value = "대학교 이름")
    private String university;
}
