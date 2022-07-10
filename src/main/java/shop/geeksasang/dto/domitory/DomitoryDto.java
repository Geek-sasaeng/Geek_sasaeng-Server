package shop.geeksasang.dto.domitory;

import lombok.Data;
import shop.geeksasang.domain.Domitory;

@Data
public class DomitoryDto {

    private int domitory_id;
    private String domitory_name;
    private int university_id;
    private String university_name;

    public DomitoryDto(Domitory domitory) {
        this.domitory_id = domitory.getId();
        this.domitory_name = domitory.getName();
        this.university_id = domitory.getUniversity().getId();
        this.university_name = domitory.getUniversity().getName();
    }
}
