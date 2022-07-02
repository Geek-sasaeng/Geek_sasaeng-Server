package shop.geeksasang.dto.deliveryParty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import shop.geeksasang.config.domain.MatchingStatus;
import shop.geeksasang.config.domain.Status;
import shop.geeksasang.domain.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TempDto {
            private String createdAt;   ;
            private String updatedAt;
            private Status status ;
            private int id ;
            private List<HashTag> hashTag;
            private String title;
            private String content;
            private LocalDateTime  orderTime;
            private int currentMatching;
            private int maxMatching;
            private String location;
            private MatchingStatus matchingStatus;
            private String userName;
            private String profileImgUrl;

    public TempDto(DeliveryParty deliveryParty) {
        this.createdAt = deliveryParty.getCreatedAt();
        this.updatedAt = deliveryParty.getUpdatedAt();
        this.status = deliveryParty.getStatus();
        this.id = deliveryParty.getId();
        this.hashTag = deliveryParty.getHashTag();
        this.title = deliveryParty.getTitle();
        this.content = deliveryParty.getContent();
        this.orderTime = deliveryParty.getOrderTime();
        this.currentMatching = deliveryParty.getCurrentMatching();
        this.maxMatching = deliveryParty.getMaxMatching();
        this.location = deliveryParty.getLocation();
        this.matchingStatus = deliveryParty.getMatchingStatus();
        this.userName = deliveryParty.getChief().getNickName();
        this.profileImgUrl = null;
    }
}
