package shop.geeksasang.dto.chatroom;

import lombok.Getter;

@Getter
public class GetChatRoomsRes {

    private String uuid;

    public GetChatRoomsRes(String uuid) {
        this.uuid = uuid;
    }
}
