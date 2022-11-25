package shop.geeksasang.rabbitmq;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ChattingVO {
    private String ChatRoomUUID;
    private String chattingId;
    private String email;
    private String msg;
}
