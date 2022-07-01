package shop.geeksasang.dto.login;

import lombok.*;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginReq {
    @Size(min = 6)
    private String loginId;

    @Size(min = 8)
    private String password;
}
