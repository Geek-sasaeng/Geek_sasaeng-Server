package shop.geeksasang.dto.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.domain.chat.PartyChatRoom;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetPartyChattingRoomsReq {

    private List<PartyChatRoom> parties;
    private Boolean finalPage;

    public GetPartyChattingRoomsReq(List<PartyChatRoom> parties, Boolean finalPage) {
        this.parties = parties;
        this.finalPage = finalPage;
    }
}
