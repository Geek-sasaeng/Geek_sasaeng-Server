package shop.geeksasang.domain;

import lombok.Getter;
import shop.geeksasang.config.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
public class Commercial extends BaseEntity {

    @Id @GeneratedValue
    @Column(name="commercial_id")
    private int id;

    private String imgUrl;
}
