package shop.geeksasang.dto.login;

import lombok.*;
import shop.geeksasang.domain.University;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtInfo {
    private int universityId;
    private int userId;
}
