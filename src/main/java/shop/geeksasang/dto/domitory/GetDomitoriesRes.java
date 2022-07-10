package shop.geeksasang.dto.domitory;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Domitory;

@Builder
@Getter
@Setter
public class GetDomitoriesRes {

    private int id;
    private String name;

    public GetDomitoriesRes(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static GetDomitoriesRes of(Domitory domitory) {
        return new GetDomitoriesRes(domitory.getId(), domitory.getName());
    }
}
