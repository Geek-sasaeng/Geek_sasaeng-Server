package shop.geeksasang.domain.announcement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;
import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Announcement extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String content;
    private String imgUrl;
}
