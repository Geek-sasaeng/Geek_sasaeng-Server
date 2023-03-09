package shop.geeksasang.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="grade_id")
    private int id;

    private String name; //등급 이름

    private int standard; //등급 판단 기준

    public Grade(String name, int standard) {
        this.name = name;
        this.standard = standard;
    }
}
