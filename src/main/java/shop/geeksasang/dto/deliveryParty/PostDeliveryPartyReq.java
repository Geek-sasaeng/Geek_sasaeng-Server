package shop.geeksasang.dto.deliveryParty;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
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

    // 추후 jwt 이용
    @ApiModelProperty(example = "1")
    @ApiParam(value = "사용자 id", required = true)
    @NotNull
    private int chief;

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

    @ApiModelProperty(example = "2022-07-13T16:29:30.97")
    @ApiParam(value = "주문 시간", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @NotNull
    @FutureOrPresent //현재 or 미래 validation
    private LocalDateTime orderTime; //주문시간

    @ApiModelProperty(example = "4")
    @ApiParam(value = "매칭 인원", required = true)
    @NotNull
    private int maxMatching;

    @ApiModelProperty(example = "1기숙사 정문")
    @ApiParam(value = "수령 장소", required = true)
    @NotBlank(message = "수령 장소를 입력하세요.")
    private String location;

    // 외래키 참조하는 것 말고는  요청 엔티티 생성
    public DeliveryParty toEntity() {
        return DeliveryParty.builder()
                .title(getTitle())
                .content(getContent())
                .orderTime(getOrderTime())
                .maxMatching(getMaxMatching())
                .location(getLocation())
                .build();
    }
}
