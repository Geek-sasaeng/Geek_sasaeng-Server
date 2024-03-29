package shop.geeksasang.domain.deliveryparty;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.status.BaseStatus;

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

    public DeliveryPartyHashTag(DeliveryParty deliveryParty, HashTag hashTag){
        this.deliveryParty=deliveryParty;
        this.hashTag=hashTag;
        super.setStatus(BaseStatus.ACTIVE);
    }
}
