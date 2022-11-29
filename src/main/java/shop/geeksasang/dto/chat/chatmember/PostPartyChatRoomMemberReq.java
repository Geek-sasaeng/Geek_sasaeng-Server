package shop.geeksasang.dto.chat.chatmember;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
public class PostPartyChatRoomMemberReq {

    @ApiModelProperty(example = "637fa741bba4cf6c34bc13ef")
    @ApiParam(value = "배달 파티 채팅방 ID", required = true)
    @NotBlank(message = "배달 파티 채팅방 제목을 입력하세요.")
    private String partyChatRoomId;
}
