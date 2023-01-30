package shop.geeksasang.dto.chat.chatmember;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.domain.chat.PartyChatRoomMember;
import shop.geeksasang.domain.deliveryparty.DeliveryPartyMember;
import shop.geeksasang.domain.member.Member;

@Getter
@NoArgsConstructor
public class GetPartyChatRoomMembersInfoRes {

    private String memberUuid;

    private Integer memberId;

    private String userName;

    private String userProfileImgUrl;

    public GetPartyChatRoomMembersInfoRes(String memberUuid, Integer memberId, String userName, String userProfileImgUrl) {
        this.memberUuid = memberUuid;
        this.memberId = memberId;
        this.userName = userName;
        this.userProfileImgUrl = userProfileImgUrl;
    }

    public static GetPartyChatRoomMembersInfoRes from(DeliveryPartyMember member, PartyChatRoomMember chatRoomMember) {
        Member participant = member.getParticipant();
        return new GetPartyChatRoomMembersInfoRes(chatRoomMember.getId(), participant.getId(), participant.getNickName(), participant.getProfileImgUrl());
    }
}
