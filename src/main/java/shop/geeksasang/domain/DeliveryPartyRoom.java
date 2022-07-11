package shop.geeksasang.domain;

import lombok.*;

import javax.persistence.*;

import shop.geeksasang.config.domain.BaseEntity;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Setter
public class DeliveryPartyRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="delivery_party_room_id")
    private int id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member participant;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="delivery_party_id")
    private DeliveryParty party;

    //-// connect 메서드 //-//
    public void connectParticipant(Member participant){
        this.participant = participant;
    }
    public void connectParty(DeliveryParty party){
        this.party=party;
    }

}