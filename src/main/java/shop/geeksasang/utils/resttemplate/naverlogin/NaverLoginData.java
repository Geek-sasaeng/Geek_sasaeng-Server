package shop.geeksasang.utils.resttemplate.naverlogin;

import lombok.Data;

import java.io.Serializable;

@Data
public class NaverLoginData {
    private String id;
    private String email;
    private String mobile;
    private String mobile_e164;
}
