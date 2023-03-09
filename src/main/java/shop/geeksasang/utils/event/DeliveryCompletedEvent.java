package shop.geeksasang.utils.event;

import lombok.Getter;

@Getter
public class DeliveryCompletedEvent {
    private int memberId;

    public DeliveryCompletedEvent(int memberId) {
        this.memberId = memberId;
    }
}
