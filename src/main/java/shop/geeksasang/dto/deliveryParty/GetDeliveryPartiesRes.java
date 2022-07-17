package shop.geeksasang.dto.deliveryParty;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.DeliveryPartyHashTag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
public class GetDeliveryPartiesRes {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "배달 파티 id(pk)")
    private int id;

    @ApiModelProperty(example = "피자 먹을 사람~!")
    @ApiParam(value = "배달 파티 제목")
    private String title;

    @ApiModelProperty(example = "2022-07-29 20:29:30")
    @ApiParam(value = "배달 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderTime;

    @ApiModelProperty(example = "2")
    @ApiParam(value = "현재 배달 파티 참여 인원")
    private int currentMatching;

    @ApiModelProperty(example = "4")
    @ApiParam(value = "배달 파티에 참여할 수 있는 총 인원")
    private int maxMatching;

    @ApiModelProperty(example = "깉이 먹고 싶어요")
    @ApiParam(value = "해시태그")
    private List<String> hashTags;

    static public GetDeliveryPartiesRes toDto(DeliveryParty deliveryParty){
        List<String> hashTagDto = makeHashTagEntityToDto(deliveryParty.getDeliveryPartyHashTags());
        return  GetDeliveryPartiesRes.builder()
                .id(deliveryParty.getId())
                .title(deliveryParty.getTitle())
                .orderTime(deliveryParty.getOrderTime())
                .currentMatching(deliveryParty.getCurrentMatching())
                .maxMatching(deliveryParty.getMaxMatching())
                .hashTags(hashTagDto)
                .build();
    }

    private static List<String> makeHashTagEntityToDto(List<DeliveryPartyHashTag> deliveryPartyHashTags) {
        return deliveryPartyHashTags.stream()
                .map(deliveryPartyHashTag -> deliveryPartyHashTag.getHashTag().getTitle())
                .collect(Collectors.toList());
    }
}
