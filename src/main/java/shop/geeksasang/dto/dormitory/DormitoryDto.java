package shop.geeksasang.dto.dormitory;

import lombok.Data;
import shop.geeksasang.domain.Dormitory;

@Data
public class DormitoryDto {

    private int dormitory_id;
    private String dormitory_name;
    private int university_id;
    private String university_name;

    public DormitoryDto(Dormitory dormitory) {
        this.dormitory_id = dormitory.getId();
        this.dormitory_name = dormitory.getName();
        this.university_id = dormitory.getUniversity().getId();
        this.university_name = dormitory.getUniversity().getName();
    }
}
