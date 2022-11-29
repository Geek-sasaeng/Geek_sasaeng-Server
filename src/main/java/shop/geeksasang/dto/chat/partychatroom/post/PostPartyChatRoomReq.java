package shop.geeksasang.dto.chat.partychatroom.post;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
public class PostPartyChatRoomReq {

    @ApiModelProperty(example = "치킨 시키실 분 구합니다.")
    @ApiParam(value = "배달 파티 채팅방 제목", required = true)
    @Size(min = 1, max = 20)
    @NotBlank(message = "배달 파티 채팅방 제목을 입력하세요.")
    private String title;

    @ApiModelProperty(example = "111-22222-33333")
    @ApiParam(value = "계좌번호", required = true)
    @NotBlank(message = "계좌번호를 입력하세요.")
    private String accountNumber;

    @ApiModelProperty(example = "신한은행")
    @ApiParam(value = "은행이름", required = true)
    @NotBlank(message = "은행이름을 입력하세요.")
    private String bank;

    @ApiModelProperty(example = "Delivery")
    @ApiParam(value = "채팅방 종류", required = true)
    @NotBlank(message = "배달 파티 채팅방 종류를 입력하세요.")
    private String category;

    @ApiModelProperty(example = "4")
    @ApiParam(value = "매칭 최대 인원", required = true)
    private Integer maxMatching;
}
