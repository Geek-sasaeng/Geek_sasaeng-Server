package shop.geeksasang.domain.deliveryparty;

import lombok.*;

import javax.persistence.*;

import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.status.AccountTransferStatus;
import shop.geeksasang.config.status.BaseStatus;
import shop.geeksasang.domain.member.Member;

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

    @Enumerated(EnumType.STRING)
    private AccountTransferStatus acccountTransferStatus; // 계좌이체 완료상태


    /*
    연관관계 편의 메서드
     */
    public DeliveryPartyMember(Member participant, DeliveryParty party) {
        this.participant = participant;
        this.party = party;
        party.addPartyMember(this);
        super.setStatus(BaseStatus.ACTIVE);
    }

    public void leaveDeliveryParty(){
        changeStatusToInactive();
    }

    // 배달파티 멤버 삭제
    private void changeStatusToInactive(){
        super.setStatus(BaseStatus.INACTIVE);
    }

    public void changeAccountTransferStatusToY(){
        this.acccountTransferStatus = AccountTransferStatus.Y;
    }

    public void changeAccountTransferStatusToN(){
        this.acccountTransferStatus = AccountTransferStatus.N;
    }

    public boolean isActive(){
        if(getStatus() == BaseStatus.ACTIVE) return true;
        return false;
    }

    @Override
    public String toString() {
        return "DeliveryPartyMember{" +
                "id=" + id +
                ", participant=" + participant +
                ", party=" + party +
                ", menuList=" + menuList +
                ", acccountTransferStatus=" + acccountTransferStatus +
                ", status " + super.getStatus() +
                '}';
    }
}