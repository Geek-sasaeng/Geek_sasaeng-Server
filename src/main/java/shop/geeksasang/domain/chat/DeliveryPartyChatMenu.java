package shop.geeksasang.domain.chat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class DeliveryPartyChatMenu extends BaseEntity {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="delivery_party_chat_room")
    private DeliveryPartyChatRoom deliveryPartyChatRoom;

    private String title;

    private int price;

    public DeliveryPartyChatMenu(DeliveryPartyChatRoom deliveryPartyChatRoom, String title, int price) {
        this.deliveryPartyChatRoom = deliveryPartyChatRoom;
        this.title = title;
        this.price = price;
    }
}
