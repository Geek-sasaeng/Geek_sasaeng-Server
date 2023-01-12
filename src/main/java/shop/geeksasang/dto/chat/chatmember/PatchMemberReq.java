package shop.geeksasang.dto.chat.chatmember;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class PatchMemberReq {


    @NotBlank
    private String roomId;

    public PatchMemberReq(String roomId) {
        this.roomId = roomId;
    }
}
