package shop.geeksasang.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.University;

@Getter @Setter
@Builder
@AllArgsConstructor
public class JwtInfo {
    private int universityId;
    private int userId;


}
