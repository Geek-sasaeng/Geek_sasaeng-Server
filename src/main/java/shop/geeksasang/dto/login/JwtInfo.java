package shop.geeksasang.dto.login;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtInfo {
    private int universityId;
    private int userId;
}
