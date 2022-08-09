package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.DeliveryPartyMember;
import shop.geeksasang.domain.DeliveryPartyMenu;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.deliveryPartyMenu.PostDeliveryPartyMenuReq;
import shop.geeksasang.dto.deliveryPartyMenu.PostDeliveryPartyMenuRes;
import shop.geeksasang.dto.deliveryPartyMenu.vo.MenuVo;
import shop.geeksasang.repository.DeliveryPartyMemberRepository;
import shop.geeksasang.repository.DeliveryPartyMenuRepository;
import shop.geeksasang.repository.DeliveryPartyRepository;
import shop.geeksasang.repository.MemberRepository;

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
