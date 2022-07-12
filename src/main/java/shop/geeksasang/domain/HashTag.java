package shop.geeksasang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class HashTag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hash_tag_id")
    private int id;

    private String title;

    @OneToMany(mappedBy ="hashTag", targetEntity=DeliveryPartyHashTag.class, cascade = CascadeType.ALL)
    private List<DeliveryParty> deliveryParties=new ArrayList<>();

    public void plusParties(DeliveryParty deliveryParty){
        this.deliveryParties.add(deliveryParty);
    }

}