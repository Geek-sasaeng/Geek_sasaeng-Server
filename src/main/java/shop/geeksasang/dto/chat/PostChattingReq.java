package shop.geeksasang.dto.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
@NoArgsConstructor
@Getter
public class PostChattingReq {

    @NotEmpty
    private String ChatRoomId;

    @NotEmpty
    private String content;

    public PostChattingReq(String ChatRoomId, String content) {
        this.ChatRoomId = ChatRoomId;
        this.content = content;
    }
}
