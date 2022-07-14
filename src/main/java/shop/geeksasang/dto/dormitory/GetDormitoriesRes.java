package shop.geeksasang.dto.dormitory;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Dormitory;

@Builder
@Getter
@Setter
public class GetDormitoriesRes {

    private int id;
    private String name;

    public GetDormitoriesRes(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static GetDormitoriesRes of(Dormitory dormitory) {
        return new GetDormitoriesRes(dormitory.getId(), dormitory.getName());
    }
}
