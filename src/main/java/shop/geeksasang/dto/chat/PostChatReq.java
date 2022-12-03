package shop.geeksasang.dto.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
@NoArgsConstructor
@Getter
public class PostChatReq {

    @NotEmpty
    private String content;

    @NotEmpty
    private String chatRoomId;

    private Boolean isSystemMessage;

    private int memberId;

    private String email;

    private String profileImgUrl;

    private String chatType;

    private String chatId;

    public PostChatReq(String chatRoomId, String content) {
        this.chatRoomId = chatRoomId;
        this.content = content;
    }
}
