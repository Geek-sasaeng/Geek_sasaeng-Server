package shop.geeksasang.dto.deliveryParty.patch;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PatchLeaveChiefRes {
    @ApiModelProperty(example = "기존 방장을 삭제하고 새로운 방장으로 교체했습니다.",
            value = "배달 파티의 인원이 2명 이상일 때 : " + "result\": \"기존 방장을 삭제하고 새로운 방장으로 교체했습니다." + "\n" +
                    "배달 파티의 인원이 방장 1명일 때 : " + "result\": \"파티를 삭제했습니다.\"")
    private String result;

    public PatchLeaveChiefRes(String result) {
        this.result = result;
    }
}
