package shop.geeksasang.dto.email;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/*
    [이메일 인증]
    Body: 이메일 주소, 이메일 인증번호
 */
@Data
public class EmailCertificationReq {
    @Email
    @NotBlank(message = "이메일은 필수로 입력해야합니다.")
    @ApiModelProperty(example = "abc@gachon.ac.kr")
    private String email;

    @ApiModelProperty(example = "123456")
    @NotNull(message = "인증번호는 필수로 입력해야합니다.")
    private String key;
}
