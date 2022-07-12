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

    //생성자
    public DeliveryPartyHashTag(DeliveryParty deliveryParty, HashTag hashTag){
        this.deliveryParty=deliveryParty;
        this.hashTag = hashTag;
    }

    public void connectPartyHashTag(DeliveryParty party, HashTag hashTag){

        this.deliveryParty=party;
        this.hashTag=hashTag;

        if(!party.getHashTags().contains(hashTag)) {
            System.out.println("여기들어옴");
            party.getHashTags().add(hashTag);
            System.out.println("나감");
        }

        if(!hashTag.getDeliveryParties().contains(party)){
            System.out.println("풉키풉키");
            //hashTag.getDeliveryParties().add(party);
            hashTag.plusParties(party);
            System.out.println("풉키");
        }

    }
}

