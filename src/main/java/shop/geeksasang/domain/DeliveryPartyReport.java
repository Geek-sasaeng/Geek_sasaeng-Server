package shop.geeksasang.domain;

import javax.persistence.*;

@Entity
@DiscriminatorValue("delivery_party")
public class DeliveryPartyReport extends Report{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="delivery_party_id")
    public DeliveryParty deliveryParty;

}
