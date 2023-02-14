package shop.geeksasang.dto.chat.chatmember;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetPartyChatRoomMemberProfileRes {
    @ApiModelProperty(example = "debin", value = "멤버 닉네임")
    private String userName;

    @ApiModelProperty(example = "신입생", value = "멤버 활동 등급(신입생,복학생,졸업생)")
    private String grade;

    @ApiModelProperty(example = "false", value = "방장 여부 -> true(파티장=방장), false이면 파티원")
    private Boolean isChief;

    public GetPartyChatRoomMemberProfileRes(String userName,String grade, boolean isChief) {
        this.userName = userName;
        this.grade = grade;
        this.isChief = isChief;
    }
}
