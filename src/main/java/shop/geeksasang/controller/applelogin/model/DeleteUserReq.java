package shop.geeksasang.controller.applelogin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeleteUserReq {
    int userId;
    String refreshToken;

    String identityToken;
}
