package shop.geeksasang.dto.chat.chatchief;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
public class DeleteMemberByChiefReq {

    @ApiParam(value = "배달 채팅방 id", required = true, example = "638dba87ca6fd27d74c17be8")
    @NotBlank
    private String roomId;

    @ApiParam(value = "퇴장시킬 배달 채팅방 멤버 아이디 배열", required = true, example = "[638dba87ca6fd27d74c17be8, 638dba87ca6fd27d74c17be9]")
    @NotBlank
    private List<String> removedMemberIdList;

    public DeleteMemberByChiefReq(String roomId, List<String> removedMemberIdList) {
        this.roomId = roomId;
        this.removedMemberIdList = removedMemberIdList;
    }
}
