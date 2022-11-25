package shop.geeksasang.dto.chatting.chattingmember;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
public class PostParticipantInfoReq {
    @NotEmpty
    private String chattingRoomId;

    private Boolean isRemittance;

    private Long memberId;
}
