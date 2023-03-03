package shop.geeksasang.controller.applelogin.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown=true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenResponse {

    private String access_token;
    private Long expires_in;
    private String id_token;
    private String refresh_token;
    private String token_type;
    private int userId;
    private String jwt;

    public TokenResponse(String access_token, String id_token, String refresh_token, int userId, String jwt) {
        this.access_token = access_token;
        this.id_token = id_token;
        this.refresh_token = refresh_token;
        this.userId = userId;
        this.jwt = jwt;
    }
}
