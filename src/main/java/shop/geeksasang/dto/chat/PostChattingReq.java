package shop.geeksasang.dto.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
@NoArgsConstructor
@Getter
public class PostChattingReq {

    @NotEmpty
    private String content;

    @NotEmpty
    private String chatRoomId;

    private Boolean isSystemMessage;

    private int memberId;

    private String email;

    private String profileImgUrl;

    public PostChattingReq(String chatRoomId, String content) {
        this.chatRoomId = chatRoomId;
        this.content = content;
    }
}
