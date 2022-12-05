package shop.geeksasang.dto.chat.chatchief;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class PostRemoveMemberByChiefReq {

    @ApiParam(value = "배달 채팅방 id", required = true, example = "638dba87ca6fd27d74c17be8")
    @NotBlank
    private String roomId;

    @ApiParam(value = "퇴장시킬 배달 채팅방 멤버 아이디", required = true, example = "638dba87ca6fd27d74c17be8")
    @NotBlank
    private String removedMemberId;

    public PostRemoveMemberByChiefReq(String roomId, String removedMemberId) {
        this.roomId = roomId;
        this.removedMemberId = removedMemberId;
    }
}
