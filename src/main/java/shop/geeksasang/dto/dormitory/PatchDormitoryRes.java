package shop.geeksasang.dto.dormitory;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Member;

@Getter
@Builder
public class PatchDormitoryRes {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "사용자 인덱스")
    private int id;

    @ApiModelProperty(example = "1")
    @ApiParam(value = "기숙사 인덱스")
    private int dormitoryId;

    @ApiModelProperty(example = "1기숙사")
    @ApiParam(value = "사용자 기숙사 이름")
    private String dormitoryName;

    //빌더
    static public PatchDormitoryRes toDto(Member member){
        return PatchDormitoryRes.builder()
                .id(member.getId())
                .dormitoryId(member.getDormitory().getId())
                .dormitoryName(member.getDormitory().getName())
                .build();
    }
}
