package shop.geeksasang.dto.member;

import lombok.Builder;
import lombok.Data;

@Builder // .builder() 사용
@Data // Getter, Setter 포함
public class GetCheckNickNameDuplicatedRes {
    private String nickName;
}
