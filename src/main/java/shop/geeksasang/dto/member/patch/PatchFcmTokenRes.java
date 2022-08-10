package shop.geeksasang.dto.member.patch;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Member;

@Getter
@Setter
@Builder
public class PatchFcmTokenRes {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "사용자id 인덱스")
    private int id;

    @ApiModelProperty(example = "토큰예시 발급받으면 입력할 예정")
    @ApiParam(value = "FCM 토큰값")
    private String FcmToken;

    // 빌더
    static public PatchFcmTokenRes toDto(Member member){
        return PatchFcmTokenRes.builder()
                .id(member.getId())
                .FcmToken(member.getFcmToken())
                .build();
    }
}
