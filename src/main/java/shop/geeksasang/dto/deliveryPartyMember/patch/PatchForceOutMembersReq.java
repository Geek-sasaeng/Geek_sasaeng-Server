package shop.geeksasang.dto.deliveryPartyMember.patch;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class PatchForceOutMembersReq {

    @ApiParam(value = "강제퇴장할 멤버들의 id", required = true, example = "[1,2,3]")
    @NotEmpty
    private List<Integer> membersId;

    @ApiParam(value = "파티 id", required = true, example = "1324")
    @NotNull
    private Integer partyId;

    public PatchForceOutMembersReq(List<Integer> membersId, Integer partyId) {
        this.membersId = membersId;
        this.partyId = partyId;
    }
}
