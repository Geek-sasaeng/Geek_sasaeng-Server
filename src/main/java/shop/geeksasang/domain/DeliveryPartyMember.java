package shop.geeksasang.domain;

import lombok.*;

import javax.persistence.*;

import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.status.BaseStatus;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class DeliveryPartyMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="delivery_party_member_id")
    private int id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member participant;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="delivery_party_id")
    private DeliveryParty party;

    @OneToMany(mappedBy = "menuOwner")
    private List<DeliveryPartyMenu> menuList = new ArrayList<>();


    public DeliveryPartyMember(Member participant, DeliveryParty party) {
        this.participant = participant;
        this.party = party;
        party.addPartyMember(this);
        super.setStatus(BaseStatus.ACTIVE);
    }

    // 배달파티 멤버 삭제
    public void changeStatusToInactive(){
        super.setStatus(BaseStatus.INACTIVE);
    }
}