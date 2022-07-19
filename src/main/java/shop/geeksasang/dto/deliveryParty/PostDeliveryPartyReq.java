package shop.geeksasang.dto.deliveryParty;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;
import shop.geeksasang.domain.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDeliveryPartyReq {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "기숙사 id", required = true)
    @NotNull
    private int dormitory;

    @ApiModelProperty(example = "1")
    @ApiParam(value = "음식 카테고리", required = true)
    @NotNull
    private int foodCategory;

    @ApiModelProperty(example = "[1,2]")
    @ApiParam(value = "해시태그 리스트", required = true)
    private List<Integer> hashTag;

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

    // 외래키 참조하는 것 말고는  요청 엔티티 생성
    public DeliveryParty toEntity() {
        return DeliveryParty.builder()
                .title(getTitle())
                .content(getContent())
                .orderTime(getOrderTime())
                .maxMatching(getMaxMatching())
                .location(new Location(getLatitude(),getLongitude()))
                .storeUrl(getStoreUrl())
                .build();
    }
}
