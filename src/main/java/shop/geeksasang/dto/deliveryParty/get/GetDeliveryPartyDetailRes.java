package shop.geeksasang.dto.deliveryParty.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.config.status.BelongStatus;
import shop.geeksasang.config.status.MatchingStatus;
import shop.geeksasang.domain.chat.PartyChatRoom;
import shop.geeksasang.domain.deliveryparty.DeliveryParty;


import java.time.LocalDateTime;


@Getter
@Setter
public class GetDeliveryPartyDetailRes {

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

    @ApiModelProperty(example = "1", value = "기숙사 id")
    private int dormitory;

    @ApiModelProperty(example = "07438ec2-b0e6-4771-8e1d-115bd93ce433", value = "채팅방 uuid")
    private String uuid;

    @ApiModelProperty(example = "Y", value = "사용자가 파티멤버에 속해있는지 여부. 속해있으면 Y, 아니면 N")
    private BelongStatus belongStatus;

    @ApiModelProperty(value = "배달 파티 채팅방 ID", example = "637fa741bba4cf6c34bc13ef")
    private String partyChatRoomId;

    @ApiModelProperty(value = "배달 파티 채팅방 이름", example = "같이 먹어요")
    private String partyChatRoomTitle;

    //빌더
    static public GetDeliveryPartyDetailRes toDto(DeliveryParty deliveryParty, boolean authorStatus, BelongStatus belongStatus, PartyChatRoom partyChatRoom){

        return GetDeliveryPartyDetailRes.builder()
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
                .authorStatus(authorStatus)
                .dormitory(deliveryParty.getDormitory().getId())
                .uuid(deliveryParty.getUuid())
                .belongStatus(belongStatus)
                .partyChatRoomId(partyChatRoom.getId())
                .partyChatRoomTitle(partyChatRoom.getTitle())
                .build();
    }

    @Builder
    public GetDeliveryPartyDetailRes(int id, String chief, int chiefId, String chiefProfileImgUrl, String foodCategory, boolean hashTag, String title, String content,
                                     LocalDateTime orderTime, int currentMatching, int maxMatching, MatchingStatus matchingStatus, String updatedAt, Double latitude,
                                     Double longitude, String storeUrl, boolean authorStatus, int dormitory, String uuid, BelongStatus belongStatus, String partyChatRoomId, String partyChatRoomTitle) {
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
        this.dormitory = dormitory;
        this.uuid = uuid;
        this.belongStatus = belongStatus;
        this.partyChatRoomId = partyChatRoomId;
        this.partyChatRoomTitle = partyChatRoomTitle;
    }
}
