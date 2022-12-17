package shop.geeksasang.dto.chat.chatmember;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.domain.chat.PartyChatRoom;
import shop.geeksasang.domain.chat.PartyChatRoomMember;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PartyChatRoomMemberRes {

    @ApiModelProperty(example = "637fa741bba4cf6c34bc13ef")
    @ApiParam(value = "배달 파티 채팅방 멤버 ID")
    private String partyChatRoomMemebrId;

    @ApiModelProperty(example = "2022-11-29 11:00:12")
    @ApiParam(value = "배달 파티 채팅방 입장 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime enterTime;

    @ApiModelProperty(example = "true")
    @ApiParam(value = "송금 완료 여부")
    private boolean isRemittance;

    @ApiModelProperty(example = "637fa741bba4cf6c34bc13ef")
    @ApiParam(value = "배달 파티 채팅방 ID")
    private String partyChatRoomId;

    /*
    연관관계 편의 메서드
     */
    @Builder
    public PartyChatRoomMemberRes(String partyChatRoomMemebrId, LocalDateTime enterTime, boolean isRemittance, String partyChatRoomId) {
        this.partyChatRoomMemebrId = partyChatRoomMemebrId;
        this.enterTime = enterTime;
        this.isRemittance = isRemittance;
        this.partyChatRoomId = partyChatRoomId;
    }

    public static PartyChatRoomMemberRes toDto(PartyChatRoomMember partyChatRoomMember, PartyChatRoom partyChatRoom){
        return PartyChatRoomMemberRes.builder()
                .partyChatRoomMemebrId(partyChatRoomMember.getId())
                .enterTime(partyChatRoomMember.getEnterTime())
                .isRemittance(partyChatRoomMember.isRemittance())
                .partyChatRoomId(partyChatRoom.getId())
                .build();
    }
}
