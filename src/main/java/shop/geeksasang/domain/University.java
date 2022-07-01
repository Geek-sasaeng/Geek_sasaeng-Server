package shop.geeksasang.domain;

import lombok.Getter;

import javax.persistence.*;

import shop.geeksasang.config.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class University extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="university_id")
    private int id;

    @OneToMany(mappedBy = "university")
    private List<Member> memberList = new ArrayList<>();

    private String name;

    private String universityImgUrl;
}