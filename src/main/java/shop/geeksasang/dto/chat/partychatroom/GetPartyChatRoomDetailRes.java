package shop.geeksasang.dto.chat.partychatroom;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.domain.chat.PartyChatRoom;
import shop.geeksasang.domain.chat.PartyChatRoomMember;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class GetPartyChatRoomDetailRes {

    @ApiModelProperty(example = "true", value = "송금 완료 여부(참여자)")
    private Boolean isRemittanceFinish;

    @ApiModelProperty(example = "false", value = "방장 여부")
    private Boolean isChief;

    @ApiModelProperty(example = "true", value = "주문 완료 여부")
    private Boolean isOrderFinish;

    @ApiModelProperty(example = "111-111-111111", value = "계좌번호")
    private String accountNumber;

    @ApiModelProperty(example = "신한", value = "은행")
    private String bank;

    @ApiModelProperty(example = "2023-01-03 11:00:12", value = "채팅방 최초 입장시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime enterTime;

    @ApiModelProperty(example = "88", value = "채팅방 방장 id")
    private int chiefId;


    @Builder
    public GetPartyChatRoomDetailRes(Boolean isRemittanceFinish, Boolean isChief, Boolean isOrderFinish, String accountNumber, String bank, LocalDateTime enterTime, int chiefId) {
        this.isRemittanceFinish = isRemittanceFinish;
        this.isChief = isChief;
        this.isOrderFinish = isOrderFinish;
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.enterTime = enterTime;
        this.chiefId = chiefId;
    }

    //todo : isOrderFinish 추가하기
    public static GetPartyChatRoomDetailRes toDto(PartyChatRoom partyChatRoom, PartyChatRoomMember partyChatRoomMember,Boolean isChief){
        return GetPartyChatRoomDetailRes.builder()
                .isRemittanceFinish(partyChatRoomMember.isRemittance())
                .isChief(isChief)
                .accountNumber(partyChatRoom.getAccountNumber())
                .bank(partyChatRoom.getBank())
                .enterTime(partyChatRoomMember.getEnterTime())
                .chiefId(partyChatRoom.getChief().getMemberId())
                .build();
    }
}
