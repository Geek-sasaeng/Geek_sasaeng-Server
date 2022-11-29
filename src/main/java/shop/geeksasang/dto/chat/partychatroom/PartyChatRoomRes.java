package shop.geeksasang.dto.chat.partychatroom;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.domain.chat.PartyChatRoom;

@NoArgsConstructor
@Getter
public class PartyChatRoomRes {

    @ApiModelProperty(example = "637fa741bba4cf6c34bc13ef")
    @ApiParam(value = "배달 파티 채팅방 ID")
    private String partyChatRoomId;

    @ApiModelProperty(example = "치킨 시키실 분 구합니다.")
    @ApiParam(value = "배달 파티 채팅방 제목")
    private String title;

    /*
    연관관계 편의 메서드
     */
    @Builder
    public PartyChatRoomRes(String partyChatRoomId, String title) {
        this.partyChatRoomId = partyChatRoomId;
        this.title = title;
    }

    public static PartyChatRoomRes toDto(PartyChatRoom partyChatRoom){
        return PartyChatRoomRes.builder()
                .partyChatRoomId(partyChatRoom.getId())
                .title(partyChatRoom.getTitle())
                .build();
    }
}
