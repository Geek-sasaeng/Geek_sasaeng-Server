package shop.geeksasang.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.status.BaseStatus;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.DeliveryPartyMember;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.deliveryPartyMember.PostDeliveryPartyMemberReq;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.repository.DeliveryPartyRepository;
import shop.geeksasang.repository.DeliveryPartyMemberRepository;
import shop.geeksasang.repository.MemberRepository;

import java.util.Optional;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;

@Transactional
@Service
@RequiredArgsConstructor
public class DeliveryPartyMemberService {
    private final DeliveryPartyMemberRepository deliveryPartyMemberRepository;
    private final MemberRepository memberRepository;
    private final DeliveryPartyRepository deliveryPartyRepository;

    @Transactional(readOnly = false)
    public DeliveryPartyMember joinDeliveryPartyMember(PostDeliveryPartyMemberReq dto, JwtInfo jwtInfo){

        DeliveryPartyMember deliveryPartyMember = dto.toEntity();
        int userId = jwtInfo.getUserId();

        Optional<DeliveryParty> partyOptional = deliveryPartyRepository.findDeliveryPartyById(dto.getPartyId());
        BaseStatus status = partyOptional.get().getStatus();

        //엔티티 조회
        Member participant = memberRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));

        DeliveryParty party= deliveryPartyRepository.findById(dto.getPartyId())
        .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTY));

        // 삭제된 파티에는 참여할 수 없음.
        if(status.equals(BaseStatus.INACTIVE)) {
            throw new BaseException(CAN_NOT_PARTICIPATE);
        }

        deliveryPartyMember.connectParticipant(participant);
        deliveryPartyMember.connectParty(party);
        deliveryPartyMember.changeStatusToActive();

        deliveryPartyMemberRepository.save(deliveryPartyMember);
        return deliveryPartyMember;
    }
}
