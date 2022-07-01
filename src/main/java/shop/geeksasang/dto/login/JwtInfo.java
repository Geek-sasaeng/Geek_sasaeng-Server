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
    private University university;
    private int userId;

    @Override
    public String toString() {
        return "LoginVO{" +
                "university=" + university +
                ", userId=" + userId +
                '}';
    }
}
