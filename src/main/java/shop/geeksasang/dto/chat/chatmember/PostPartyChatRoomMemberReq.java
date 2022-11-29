package shop.geeksasang.dto.chat.chatmember;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@ToString
public class PostPartyChatRoomMemberReq {
    @NotEmpty
    private String chatRoomId;

    private Boolean isRemittance;

    private int memberId;
}
