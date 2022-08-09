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
public class Dormitory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dormitory_id")
    private int id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @OneToMany(mappedBy = "dormitory")
    private List<DeliveryParty> deliveryParties = new ArrayList<>();

    private String name;

    @Embedded
    private Location location;

    @OneToMany(mappedBy = "dormitory")
    private List<Member> members;

    public void addParty(DeliveryParty party) {
        this.deliveryParties.add(party);
    }

    //테스트용
    public Dormitory(University university, String name, Location location) {
        this.university = university;
        this.name = name;
        this.location = location;
    }
}