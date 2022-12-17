package shop.geeksasang.dto.chat.partychatroom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.domain.chat.PartyChatRoom;

@Getter
@NoArgsConstructor
public class GetPartyChatRoomRes {

    @ApiModelProperty(example = "6385e6832f43b821fb685f43", value = "채팅방 id")
    private String roomId;

    @ApiModelProperty(example = "데빈의 채팅방", value = "채팅방 정보 리스트")
    private String roomTitle;

    public GetPartyChatRoomRes(String roomId, String title) {
        this.roomId = roomId;
        this.roomTitle = title;
    }

    public static GetPartyChatRoomRes of(PartyChatRoom partyChatRoom){
        return new GetPartyChatRoomRes(partyChatRoom.getId(), partyChatRoom.getTitle());
    }
}
