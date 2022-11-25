package shop.geeksasang.dto.chatting;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
@NoArgsConstructor
@Getter
public class PostChattingReq {

    @NotEmpty
    private String chattingRoomId;

    @NotEmpty
    private String content;

    public PostChattingReq(String chattingRoomId, String content) {
        this.chattingRoomId = chattingRoomId;
        this.content = content;
    }
}
