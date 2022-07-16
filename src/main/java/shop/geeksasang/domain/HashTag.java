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

    @OneToMany(mappedBy ="hashTag")
    private List<DeliveryPartyHashTag> deliveryPartyHashTags = new ArrayList<>();

    public void addDeliveryPartyHashTag(DeliveryPartyHashTag deliveryPartyHashTag) {
        deliveryPartyHashTags.add(deliveryPartyHashTag);
    }
}
