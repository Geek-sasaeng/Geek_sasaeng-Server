package shop.geeksasang.dto.chat.chatchief;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRemoveMemberByChiefReq {

    private String roomId;
    private String removedMemberId;

    public PostRemoveMemberByChiefReq(String roomId, String removedMemberId) {
        this.roomId = roomId;
        this.removedMemberId = removedMemberId;
    }
}
