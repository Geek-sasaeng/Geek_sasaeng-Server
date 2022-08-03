package shop.geeksasang.domain.chat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.status.OrderStatus;
import shop.geeksasang.domain.DeliveryParty;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("delivery_party_chat_room")
public class DeliveryPartyChatRoom extends ChatRoom {

    @OneToOne
    private DeliveryParty deliveryParty;

    private String account;

    private int deliveryTip;

    @Enumerated
    private OrderStatus orderStatus;

    @Builder
    public DeliveryPartyChatRoom(String title, DeliveryParty deliveryParty, String account, int deliveryTip) {
        super(title);
        this.deliveryParty = deliveryParty;
        this.account = account;
        this.deliveryTip = deliveryTip;
        this.orderStatus = OrderStatus.BEFORE_ORDER;
    }
}
