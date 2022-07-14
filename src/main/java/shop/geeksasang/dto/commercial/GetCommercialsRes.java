package shop.geeksasang.dto.commercial;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Commercial;

@Getter
public class GetCommercialsRes {

    private int id;
    private String imgUrl;

    public GetCommercialsRes(int id, String imgUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
    }

    public static GetCommercialsRes toDto(Commercial commercial){
        return new GetCommercialsRes(commercial.getId(), commercial.getImgUrl());
    }
}
