package shop.geeksasang.dto.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostVerifySmsReq {

    @Size(min = 6, max = 6)
    private String verifyRandomNumber;

    @Size(min = 10, max = 11)
    private String recipientPhoneNumber;
}
