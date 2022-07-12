package shop.geeksasang.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.DeliveryPartyMember;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.deliveryPartyMember.PostDeliveryPartyMemberReq;
import shop.geeksasang.repository.DeliveryPartyRepository;
import shop.geeksasang.repository.DeliveryPartyMemberRepository;
import shop.geeksasang.repository.MemberRepository;

import static shop.geeksasang.config.exception.BaseResponseStatus.*;

@Transactional
@Service
@RequiredArgsConstructor
public class DeliveryPartyMemberService {
    private final DeliveryPartyMemberRepository deliveryPartyMemberRepository;
    private final MemberRepository memberRepository;
    private final DeliveryPartyRepository deliveryPartyRepository;

    @Transactional(readOnly = false)
    public DeliveryPartyMember joinDeliveryPartyMember(PostDeliveryPartyMemberReq dto){

        DeliveryPartyMember deliveryPartyMember = dto.toEntity();
        //엔티티 조회
        Member participant = memberRepository.findById(dto.getParticipantId())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));

        DeliveryParty party= deliveryPartyRepository.findById(dto.getPartyId())
        .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTY));

        deliveryPartyMember.connectParticipant(participant);
        deliveryPartyMember.connectParty(party);

        deliveryPartyMemberRepository.save(deliveryPartyMember);
        return deliveryPartyMember;
    }
}
