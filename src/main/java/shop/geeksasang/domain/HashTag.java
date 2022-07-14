package shop.geeksasang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hash_tag_id")
    private int id;

    private String title;

    @OneToMany(mappedBy ="hashTag", targetEntity=DeliveryPartyHashTag.class)
    private List<DeliveryParty> deliveryParties=new ArrayList<>();

    public void updateDeliveryParties(DeliveryParty deliveryParty){
        this.deliveryParties.add(deliveryParty);
    }

}