package shop.geeksasang.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/*
    이메일 주소 받아오는 DTO
 */
@Data
public class EmailReq {
    @Email
    @NotBlank(message = "이메일(필수)")
    private String email;
}