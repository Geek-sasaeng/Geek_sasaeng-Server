package shop.geeksasang.dto.notification.get;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GetAnnouncementDetailReq {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "공지사항 id 값", required = true)
    private int announcementId;
}
