package shop.geeksasang.dto.email;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/*
     [이메일 인증]
    이메일 주소 DTO
 */
@Data
public class EmailReq {
    @Email
    @NotBlank(message = "이메일(필수)")
    private String email;
}
