package shop.geeksasang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class DeliveryPartyHashTag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="delivery_party_hash_tag__id")
    private int id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="hash_tag_id")
    private HashTag hashTag;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="delivery_party_id")
    private DeliveryParty deliveryParty;

    public void connectPartyHashTag(DeliveryParty party, HashTag hashTag){

        this.deliveryParty=party;
        this.hashTag=hashTag;

        if(!party.getHashTags().contains(hashTag)) {
            party.getHashTags().add(hashTag);
        }
        if(!hashTag.getDeliveryParties().contains(party)){
            hashTag.getDeliveryParties().add(party);
        }

    }
}

