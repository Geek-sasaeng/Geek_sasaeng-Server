package shop.geeksasang.dto.chat.partychatroom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetPartyChatRoomsRes {

    @ApiModelProperty(value = "채팅방 정보 리스트")
    private List<GetPartyChatRoomRes> parties;

    @ApiModelProperty(example = "true", value = "마지막 페이지 여부 - 마지막: true / 아니면 : false")
    private Boolean finalPage;

    public GetPartyChatRoomsRes(List<GetPartyChatRoomRes> parties, Boolean finalPage) {
        this.parties = parties;
        this.finalPage = finalPage;
    }
}
