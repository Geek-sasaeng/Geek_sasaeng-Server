package shop.geeksasang.dto.deliveryParty;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.DeliveryPartyHashTag;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@Data
public class PatchDeliveryPartyStatusRes {

    private int deliveryPartyId;

    private String status;
}
