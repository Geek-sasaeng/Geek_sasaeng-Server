package shop.geeksasang.rabbitmq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChattingVO {
    private String chattingRoomUUID;
    private String chattingId;
    private String email;
    private String msg;
}
