package shop.geeksasang.domain.deliveryparty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.status.BaseStatus;

@NoArgsConstructor
@Builder
@Getter
@Entity
public class FoodCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="food_category_id")
    private int id;

    private String title;

    //테스트 용
    public FoodCategory(int id, String title) {
        setStatus(BaseStatus.ACTIVE);
        this.id = id;
        this.title = title;
    }
}