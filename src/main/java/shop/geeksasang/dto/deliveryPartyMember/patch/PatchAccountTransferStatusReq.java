package shop.geeksasang.dto.deliveryPartyMember.patch;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.geeksasang.config.status.AccountTransferStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PatchAccountTransferStatusReq {
    @ApiModelProperty(example = "1")
    @ApiParam(value = "파티 id", required = true)
    @NotNull
    private int partyId;

//    @ApiModelProperty(example = "Y")
//    @ApiParam(value = "송금 완료상태", required = true)
//    private AccountTransferStatus accountTransferStatus;
}
