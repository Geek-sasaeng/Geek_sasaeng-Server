package shop.geeksasang.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;
import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Notification extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String content;
    private String imgUrl;
}
