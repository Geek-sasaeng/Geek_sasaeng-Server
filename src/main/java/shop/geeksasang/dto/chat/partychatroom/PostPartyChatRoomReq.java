package shop.geeksasang.dto.chat.partyChatRoom;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class PostPartyChatRoomReq {
    @NotBlank
    private String title;
}
