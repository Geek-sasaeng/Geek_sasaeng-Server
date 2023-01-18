package shop.geeksasang.dto.chat.partychatroom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class PatchOrderReq {
    @ApiModelProperty(example = "639ddf7e08c0c27e2d5e6090", value = "배달파티 채팅방 id", required = true)
    @NotBlank(message = "배달 파티 채팅방 id를 입력하세요.")
    private String roomId;
}
