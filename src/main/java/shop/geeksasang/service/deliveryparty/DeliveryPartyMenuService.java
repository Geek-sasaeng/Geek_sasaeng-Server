package shop.geeksasang.service.deliveryparty;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.deliveryparty.DeliveryParty;
import shop.geeksasang.domain.deliveryparty.DeliveryPartyMember;
import shop.geeksasang.domain.deliveryparty.DeliveryPartyMenu;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.dto.deliveryPartyMenu.PostDeliveryPartyMenuReq;
import shop.geeksasang.dto.deliveryPartyMenu.vo.MenuVo;
import shop.geeksasang.repository.deliveryparty.DeliveryPartyMemberRepository;
import shop.geeksasang.repository.deliveryparty.DeliveryPartyMenuRepository;
import shop.geeksasang.repository.deliveryparty.DeliveryPartyRepository;
import shop.geeksasang.repository.member.MemberRepository;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class DeliveryPartyMenuService {

    private final DeliveryPartyRepository deliveryPartyRepository;
    private final MemberRepository memberRepository;
    private final DeliveryPartyMenuRepository deliveryPartyMenuRepository;
    private final DeliveryPartyMemberRepository deliveryPartyMemberRepository;

    public void registerDeliveryPartyMenu(PostDeliveryPartyMenuReq dto, int userId){
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));

        DeliveryParty party = deliveryPartyRepository.findDeliveryPartyByIdAndStatus(dto.getPartyId())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTY));

        DeliveryPartyMember menuOwner = deliveryPartyMemberRepository.findDeliveryPartyMemberByMemberIdAndDeliveryPartyId(member.getId(), party.getId())
                .orElseThrow(() -> new IllegalArgumentException("파티 멤버가 아닙니다"));

        for (MenuVo menuVo : dto.getMenuList()) {
            DeliveryPartyMenu menu = new DeliveryPartyMenu(menuOwner, menuVo.getName(), menuVo.getPrice());
            deliveryPartyMenuRepository.save(menu);
        }
    }
}
