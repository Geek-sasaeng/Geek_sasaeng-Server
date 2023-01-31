package shop.geeksasang.dto.member.get;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.domain.university.Dormitory;

@Getter
@NoArgsConstructor
public class GetMemberDormitoryRes {


    @ApiModelProperty(example = "제 1 기숙사")
    @ApiParam(value = "기숙사 이름")
    private String dormitoryName;

    @ApiModelProperty(example = "2")
    @ApiParam(value = "기숙사 id")
    private Integer dormitoryId;

    public GetMemberDormitoryRes(String dormitoryName, Integer dormitoryId) {
        this.dormitoryName = dormitoryName;
        this.dormitoryId = dormitoryId;
    }

    public static GetMemberDormitoryRes of(Dormitory dormitory) {
        return new GetMemberDormitoryRes(dormitory.getName(), dormitory.getId());
    }
}
