package shop.geeksasang.dto.deliveryPartyMember.patch;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.config.status.AccountTransferStatus;
import shop.geeksasang.domain.deliveryparty.DeliveryPartyMember;

@Getter
@Setter
public class PatchAccountTransferStatusRes {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "배달파티멤버 Id")
    private int deliveryPartyMemberId;

    @ApiModelProperty(example = "1")
    @ApiParam(value = "배달 파티 Id")
    private int deliveryPartyId;

    @ApiModelProperty(example = "Y")
    @ApiParam(value = "송금 완료상태", required = true)
    private AccountTransferStatus accountTransferStatus;

    static public PatchAccountTransferStatusRes toDto(DeliveryPartyMember deliveryPartyMember){
        return PatchAccountTransferStatusRes.builder()
                .deliveryPartyMemberId(deliveryPartyMember.getId())
                .deliveryPartyId(deliveryPartyMember.getParty().getId())
                .accountTransferStatus(deliveryPartyMember.getAcccountTransferStatus())
                .build();
    }

    @Builder
    public PatchAccountTransferStatusRes(int deliveryPartyMemberId, int deliveryPartyId, AccountTransferStatus accountTransferStatus){
        this.deliveryPartyMemberId = deliveryPartyMemberId;
        this.deliveryPartyId = deliveryPartyId;
        this.accountTransferStatus = accountTransferStatus;
    }
}
