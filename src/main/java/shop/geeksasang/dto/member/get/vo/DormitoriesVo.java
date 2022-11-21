package shop.geeksasang.dto.member.get.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Dormitory;

@Getter @Setter
public class DormitoriesVo {
    @ApiModelProperty(example = "1", value = "기숙사 id" )
    private int dormitoryId;

    @ApiModelProperty(example = "1기숙사", value = "기숙사 이름" )
    private String dormitoryName;


    public DormitoriesVo(Dormitory dormitory){
        this.dormitoryId  = dormitory.getId();
        this.dormitoryName = dormitory.getName();
    }
}
