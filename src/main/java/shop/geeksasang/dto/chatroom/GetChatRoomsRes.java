package shop.geeksasang.dto.chatroom;

import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;

@Getter
public class GetChatRoomsRes {

    @ApiModelProperty(example = "a752e681-921c-41a0-91dc-65861b3d3916", value = "채팅방의 uuid")
    private String uuid;

    public GetChatRoomsRes(String uuid) {
        this.uuid = uuid;
    }
}
