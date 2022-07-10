package shop.geeksasang.dto.login;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@Builder
public class PostLoginRes {
    private String jwt;
}
