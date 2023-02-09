package shop.geeksasang.domain.member;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="grade_id")
    private int id;

    private String name; //등급 이름

    private int standard; //등급 판단 기준
}
