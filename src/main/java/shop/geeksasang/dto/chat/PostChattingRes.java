package shop.geeksasang.dto.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PostChattingRes {

    private String chattingId;

    private String email;

    @NotEmpty
    private String ChatRoomId;

    @NotEmpty
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public PostChattingRes(String ChatRoomId, String content, LocalDateTime createdAt) {
        this.ChatRoomId = ChatRoomId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public PostChattingRes(String email, String ChatRoomId, String content, LocalDateTime createdAt) {
        this.email = email;
        this.ChatRoomId = ChatRoomId;
        this.content = content;
        this.createdAt = createdAt;
    }
}
