package shop.geeksasang.dto.email;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/*
    [이메일 인증]
    Body: 이메일 주소, 학교 이름
 */
@Data
@AllArgsConstructor
public class EmailReq {
    @Email
    @NotBlank(message = "이메일은 필수로 입력해야합니다 ")
    @ApiModelProperty(example = "abc@gachon.ac.kr")
    private String email;

    @ApiModelProperty(example = "Gachon University")
    private String university;

    @ApiModelProperty(example = "fe2e3a4d-1748-31f1-be0c-92a35bdfcbd5")
    private String UUID;
}
