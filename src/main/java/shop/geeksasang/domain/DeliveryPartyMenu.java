package shop.geeksasang.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.domain.chat.DeliveryPartyChatRoom;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class DeliveryPartyMenu extends BaseEntity {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="menu_owner")
    private DeliveryPartyMember menuOwner;

    private String name;

    private int price;

    public DeliveryPartyMenu(DeliveryPartyMember menuOwner, String name, int price) {
        this.menuOwner = menuOwner;
        this.name = name;
        this.price = price;
    }
}