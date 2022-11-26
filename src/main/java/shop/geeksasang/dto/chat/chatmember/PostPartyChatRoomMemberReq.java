package shop.geeksasang.dto.chat.chatmember;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
public class PostPartyChatRoomMemberReq {
    @NotEmpty
    private String ChatRoomId;

    private Boolean isRemittance;

    private int memberId;
}
