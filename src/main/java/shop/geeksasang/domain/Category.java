package shop.geeksasang.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;
import shop.geeksasang.config.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Getter
@Entity
public class Category extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="category_id")
    private int id;

    private String title;
}