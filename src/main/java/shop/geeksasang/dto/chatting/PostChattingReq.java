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

    private int participantsCnt;

    public PostChattingReq(String chattingRoomId, String content, int participantsCnt) {
        this.chattingRoomId = chattingRoomId;
        this.content = content;
        this.participantsCnt = participantsCnt;
    }
}
