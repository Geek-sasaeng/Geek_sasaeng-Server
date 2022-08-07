package shop.geeksasang.dto.deliveryParty.put;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PutDeliveryPartyReq {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "음식 카테고리", required = true)
    @NotNull
    private int foodCategory;

    @ApiModelProperty(example = "치킨 같이 나눠먹어요")
    @ApiParam(value = "배달파티 제목", required = true)
    @Size(min = 1, max = 20)
    @NotBlank(message = "배달 파티 제목을 입력하세요.")
    private String title;

    @ApiModelProperty(example = "관심있는 분 채팅 해주세요")
    @ApiParam(value = "배달파티 내용", required = true)
    @Size(min = 1, max = 500)
    @NotBlank(message = "배달파티 내용을 입력하세요.")
    private String content;

    @ApiModelProperty(example = "2022-07-13 16:29:30")
    @ApiParam(value = "주문 시간", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @NotNull
    @FutureOrPresent //현재 or 미래 validation
    private LocalDateTime orderTime; //주문시간

    @ApiModelProperty(example = "4")
    @ApiParam(value = "매칭 인원", required = true)
    @NotNull
    private int maxMatching;

    @ApiModelProperty(example = "https://baemin.me/mUpLJ7qBk")
    @ApiParam(value = "배달 업소 url", required = true)
    @NotBlank(message = "배달 업소 url을 입력하세요.")
    private String storeUrl;

    @ApiModelProperty(example = "37.456335")
    @ApiParam(value="위도", required = true)
    @NotNull
    private Double latitude;

    @ApiModelProperty(example = "127.135331")
    @ApiParam(value="경도", required = true)
    @NotNull
    private Double longitude;

    @ApiModelProperty(example = "true")
    @ApiParam(value = "해시태그 추가 여부", required = true)
    @NotNull
    private boolean hashTag;
}
