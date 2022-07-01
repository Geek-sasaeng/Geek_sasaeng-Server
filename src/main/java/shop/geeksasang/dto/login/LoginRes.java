package shop.geeksasang.dto.login;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter @Setter
@Builder
public class LoginRes {
    private String jwt;
}
