package shop.geeksasang.dto.deliveryParty.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;
import shop.geeksasang.config.status.BelongStatus;
import shop.geeksasang.config.status.MatchingStatus;
import shop.geeksasang.domain.deliveryparty.DeliveryParty;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDeliveryPartyRes {

    @ApiModelProperty(example = "1", value = "배달파티 ID")
    private int id;

    @ApiModelProperty(example = "토마스최", value = "파티장 닉네임")
    private String chief;

    @ApiModelProperty(example = "2")
    @ApiParam(value = "파티장 memberId")
    private int chiefId;

    @ApiModelProperty(example = "http://geeksasaeng.shop/s3/neo.jpg", value = "파티장 프로필 이미지 url")
    private String chiefProfileImgUrl;

    @ApiModelProperty(example = "한식", value = "음식 카테고리")
    private String foodCategory;

    @ApiModelProperty(example = "true", value = "해시태그 추가 여부")
    private boolean hashTag;

    @ApiModelProperty(example = "한식 같이 먹어요!!", value = "배달파티 제목")
    private String title;

    @ApiModelProperty(example = "불고기 전골 먹으실분 신청하세요.", value = "배달파티 내용")
    private String content;

    @ApiModelProperty(example = "2022-07-11 15:30:00", value = "주문 예정 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderTime;

    @ApiModelProperty(example = "2", value = "현재까지 매칭 된 인원")
    private int currentMatching;

    @ApiModelProperty(example = "4", value = "최대 미칭 인원")
    private int maxMatching;

    @ApiModelProperty(example = "ONGOING", value = "배달 매칭 상태, 모집중 ONGOING, 모집마감 FINISH")
    private MatchingStatus matchingStatus;

    @ApiModelProperty(example = "2022-07-11 15:30:00", value = "배달파티 정보 업데이트 시각")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String updatedAt;

    @ApiModelProperty(example = "37.456335", value = "위도")
    private Double latitude;

    @ApiModelProperty(example = "127.135331", value =" 경도")
    private Double longitude;

    @ApiModelProperty(example = "https://baemin.me/mUpLJ7qBk", value = "배달앱 주소")
    private String storeUrl;

    @ApiModelProperty(example = "true", value = "작성자 본인 여부")
    private boolean authorStatus;

    @ApiModelProperty(example = "07438ec2-b0e6-4771-8e1d-115bd93ce433", value = "채팅방 uuid")
    private String uuid;

    @ApiModelProperty(example = "Y", value = "사용자가 파티멤버에 속해있는지 여부. 속해있으면 Y, 아니면 N")
    private BelongStatus belongStatus;

    //2차 피드백으로 추가

    @ApiModelProperty(example = "1", value = "기숙사 id")
    private int dormitoryId;

    @ApiModelProperty(example = "1기숙사", value = "기숙사 이름")
    private String dormitoryName;

    @ApiModelProperty(example = "2022-07-13 13:29:30", value = "배달 파티 생성 시간")
    private String createdAt;

    @ApiModelProperty(example = "DINNER", value = "주문시간 카테고리")
    private String orderTimeCategoryType;

    @ApiModelProperty(example = "교촌 채팅방입니다", value = "파티 채팅방 이름")
    private String chatRoomName;

    @ApiModelProperty(example = "111-22222-33333", value = "계좌번호")
    private String accountNumber;

    @ApiModelProperty(example = "신한은행", value = "은행이름")
    private String bank;

    static public PostDeliveryPartyRes toDto(DeliveryParty deliveryParty){
        return PostDeliveryPartyRes.builder()
                .id(deliveryParty.getId())
                .chief(deliveryParty.getChief().getNickName())
                .chiefId(deliveryParty.getChief().getId())
                .chiefProfileImgUrl(deliveryParty.getChief().getProfileImgUrl())
                .foodCategory(deliveryParty.getFoodCategory().getTitle())
                .hashTag(!deliveryParty.getDeliveryPartyHashTags().isEmpty())
                .title(deliveryParty.getTitle())
                .content(deliveryParty.getContent())
                .orderTime(deliveryParty.getOrderTime())
                .currentMatching(deliveryParty.getCurrentMatching())
                .maxMatching(deliveryParty.getMaxMatching())
                .matchingStatus(deliveryParty.getMatchingStatus())
                .updatedAt(deliveryParty.getUpdatedAt())
                .latitude(deliveryParty.getLocation().getLatitude())
                .longitude(deliveryParty.getLocation().getLongitude())
                .storeUrl(deliveryParty.getStoreUrl())
                .authorStatus(true)
                .uuid(deliveryParty.getUuid())
                .belongStatus(BelongStatus.Y)
                .dormitoryId(deliveryParty.getDormitory().getId())
                .dormitoryName(deliveryParty.getDormitory().getName())
                .createdAt(deliveryParty.getCreatedAt())
                .orderTimeCategoryType(deliveryParty.getOrderTimeCategory().toString())
                .chatRoomName(deliveryParty.getChatRoomName())
                .accountNumber(deliveryParty.getAccountNumber())
                .bank(deliveryParty.getBank())
                .build();
    }

    @Builder
    public PostDeliveryPartyRes(int id, String chief, int chiefId, String chiefProfileImgUrl, String foodCategory, boolean hashTag, String title, String content,
                                LocalDateTime orderTime, int currentMatching, int maxMatching, MatchingStatus matchingStatus, String updatedAt, Double latitude,
                                Double longitude, String storeUrl, boolean authorStatus, String uuid, BelongStatus belongStatus, int dormitoryId,
                                String dormitoryName, String createdAt, String orderTimeCategoryType, String chatRoomName, String accountNumber, String bank) {
        this.id = id;
        this.chief = chief;
        this.chiefId = chiefId;
        this.chiefProfileImgUrl = chiefProfileImgUrl;
        this.foodCategory = foodCategory;
        this.hashTag = hashTag;
        this.title = title;
        this.content = content;
        this.orderTime = orderTime;
        this.currentMatching = currentMatching;
        this.maxMatching = maxMatching;
        this.matchingStatus = matchingStatus;
        this.updatedAt = updatedAt;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storeUrl = storeUrl;
        this.authorStatus = authorStatus;
        this.uuid = uuid;
        this.belongStatus = belongStatus;
        this.dormitoryId = dormitoryId;
        this.dormitoryName = dormitoryName;
        this.createdAt = createdAt;
        this.orderTimeCategoryType = orderTimeCategoryType;
        this.chatRoomName = chatRoomName;
        this.accountNumber = accountNumber;
        this.bank = bank;
    }

    //테스트용
    public PostDeliveryPartyRes(String dormitory, String foodCategory, String title) {
        this.foodCategory = foodCategory;
        this.title = title;
    }
}


