package shop.geeksasang.dto.chat.chatmember;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.status.AccountTransferStatus;
import shop.geeksasang.domain.chat.PartyChatRoomMember;
import shop.geeksasang.domain.deliveryparty.DeliveryPartyMember;
import shop.geeksasang.domain.member.Member;

@Getter
@NoArgsConstructor
public class GetPartyChatRoomMembersInfoRes {

    @ApiModelProperty(example = "63d77adea2639d2b72c7e743", value = "멤버 uuid")
    private String chatMemberId;

    @ApiModelProperty(example = "22", value = "맴버 id")
    private Integer memberId;

    @ApiModelProperty(example = "debin", value = "멤버 닉네임")
    private String userName;

    @ApiModelProperty(
            example = "https://geeksasaeng-s3.s3.ap-northeast-2.amazonaws.com/%ED%94%84%EB%A1%9C%ED%95%84+%EC%9D%B4%EB%AF%B8%EC%A7%80/baseProfileImg.png",
            value = "멤버 프로필 url"
    )
    private String userProfileImgUrl;

    @ApiModelProperty(
            example = "Y",
            value = "송금을 완료했는지"
    )
    private AccountTransferStatus accountTransferStatus;

    public GetPartyChatRoomMembersInfoRes(String chatMemberId, Integer memberId, String userName, String userProfileImgUrl, AccountTransferStatus accountTransferStatus) {
        this.chatMemberId = chatMemberId;
        this.memberId = memberId;
        this.userName = userName;
        this.userProfileImgUrl = userProfileImgUrl;
        this.accountTransferStatus = accountTransferStatus;
    }

    public static GetPartyChatRoomMembersInfoRes from(DeliveryPartyMember member, PartyChatRoomMember chatRoomMember) {
        Member participant = member.getParticipant();
        return new GetPartyChatRoomMembersInfoRes(
                chatRoomMember.getId(), participant.getId(), participant.getNickName(), participant.getProfileImgUrl(), member.getAcccountTransferStatus()
        );
    }
}
