package shop.geeksasang.dto.chat.chatchief;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PatchChiefReq {


    @NotBlank
    private String roomId;

    public PatchChiefReq(String roomId) {
        this.roomId = roomId;
    }
}
