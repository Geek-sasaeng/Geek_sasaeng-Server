package shop.geeksasang.dto.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostSmsReq {

    @Size(min = 10, max = 11)
    private String recipientPhoneNumber;
}