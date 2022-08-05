package shop.geeksasang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Dormitory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dormitory_id")
    private int id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @OneToMany(mappedBy = "dormitory")
    private List<DeliveryParty> deliveryParties;

    private String name;

    @Embedded
    private Location location;

    @OneToMany(mappedBy = "dormitory")
    private List<Member> members;

    public void addParty(DeliveryParty party) {
        this.deliveryParties.add(party);
    }
}