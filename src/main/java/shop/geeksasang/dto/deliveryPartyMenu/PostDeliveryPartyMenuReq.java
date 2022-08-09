package shop.geeksasang.dto.deliveryPartyMenu;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import lombok.*;
import shop.geeksasang.dto.deliveryPartyMenu.vo.MenuVo;

import javax.validation.constraints.NotNull;

import java.util.List;

@Getter @Setter
public class PostDeliveryPartyMenuReq {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "파티 id", required = true)
    @NotNull
    private int partyId;

    private List<MenuVo> menuList;

    private int numberOfMenu;
}
