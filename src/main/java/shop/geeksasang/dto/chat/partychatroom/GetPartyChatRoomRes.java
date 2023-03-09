package shop.geeksasang.dto.chat.partychatroom;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntityMongo;
import shop.geeksasang.config.status.BaseStatus;
import shop.geeksasang.domain.chat.PartyChatRoom;
import shop.geeksasang.domain.chat.PartyChatRoomMember;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetPartyChatRoomRes {

    @ApiModelProperty(example = "6385e6832f43b821fb685f43", value = "채팅방 id")
    private String roomId;

    @ApiModelProperty(example = "데빈의 채팅방", value = "채팅방 정보 리스트")
    private String roomTitle;

    @ApiModelProperty(example = "2023-01-03 11:00:12", value = "마지막 채팅을 보낸 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastChatTime;

    @ApiModelProperty(example = "2023-01-03 11:00:12", value = "채팅방 최초 입장시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime enterTime;

    @ApiModelProperty(example = "true", value = "강제 퇴장 당한 상황인지")
    private boolean forcedOut;


    public GetPartyChatRoomRes(String roomId, String roomTitle, LocalDateTime lastChatTime, LocalDateTime enterTime, boolean forcedOut) {
        this.roomId = roomId;
        this.roomTitle = roomTitle;
        this.lastChatTime = lastChatTime;
        this.enterTime = enterTime;
        this.forcedOut = forcedOut;
    }

    public static GetPartyChatRoomRes from(PartyChatRoom partyChatRoom, int memberId) {
        return new GetPartyChatRoomRes(
                partyChatRoom.getId(), partyChatRoom.getTitle(), partyChatRoom.getLastChatAt(), partyChatRoom.findMember(memberId).getEnterTime(),
                checkForcedOut(partyChatRoom.findMember(memberId).getBaseEntityMongo())
        );
    }

    private static boolean checkForcedOut(BaseEntityMongo baseEntityMongo) {
        if(baseEntityMongo.isForceOut()) return true;
        return false;
    }
}
