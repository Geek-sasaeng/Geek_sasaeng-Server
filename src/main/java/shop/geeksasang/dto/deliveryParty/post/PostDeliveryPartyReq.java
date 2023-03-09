package shop.geeksasang.dto.deliveryParty.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;
import shop.geeksasang.domain.deliveryparty.DeliveryParty;
import shop.geeksasang.domain.location.Location;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Data
@NoArgsConstructor
public class PostDeliveryPartyReq {

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

    @ApiModelProperty(example = "신한은행")
    @ApiParam(value = "은행이름", required = true)
    @NotBlank(message = "은행이름을 입력하세요.")
    private String bank;

    @ApiModelProperty(example = "111-22222-33333")
    @ApiParam(value = "계좌번호", required = true)
    @NotBlank(message = "계좌번호를 입력하세요.")
    private String accountNumber;

    @ApiModelProperty(example = "교촌 채팅방입니다")
    @ApiParam(value = "파티 채팅방 이름", required = true)
    @NotBlank(message = "채팅방 이름을 입력하세요")
    private String chatRoomName;


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

    //테스트용
    @Builder
    public PostDeliveryPartyReq(int foodCategory, String title, String content, LocalDateTime orderTime, int maxMatching, String storeUrl, Double latitude, Double longitude, boolean hashTag, String bank, String accountNumber, String chatRoomName) {
        this.foodCategory = foodCategory;
        this.title = title;
        this.content = content;
        this.orderTime = orderTime;
        this.maxMatching = maxMatching;
        this.storeUrl = storeUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hashTag = hashTag;
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.chatRoomName = chatRoomName;
    }
}
