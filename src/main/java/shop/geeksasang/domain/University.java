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
@Entity
@Getter
public class University extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="university_id")
    private int id;

    private String name;

    private String emailAddress;

    private String universityImgUrl;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
    List<Dormitory> dormitories = new ArrayList<>();

    //테스트용
    public University(String name, String emailAddress, String universityImgUrl) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.universityImgUrl = universityImgUrl;
    }
}