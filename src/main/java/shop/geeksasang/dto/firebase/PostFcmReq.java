package shop.geeksasang.dto.firebase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostFcmReq {

    private String targetToken;
    private String title;
    private String body;

}
