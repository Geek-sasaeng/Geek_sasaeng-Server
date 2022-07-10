package shop.geeksasang.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.DeliveryPartyRoom;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.deliveryPartyRoom.PostDeliveryPartyRoomReq;
import shop.geeksasang.repository.DeliveryPartyRepository;
import shop.geeksasang.repository.DeliveryPartyRoomRepository;
import shop.geeksasang.repository.MemberRepository;

import static shop.geeksasang.config.exception.BaseResponseStatus.*;

@Transactional
@Service
@RequiredArgsConstructor
public class DeliveryPartyRoomService {
    private final DeliveryPartyRoomRepository deliveryPartyRoomRepository;
    private final MemberRepository memberRepository;
    private final DeliveryPartyRepository deliveryPartyRepository;

    @Transactional(readOnly = false)
    public DeliveryPartyRoom joinDeliveryPartyRoom(PostDeliveryPartyRoomReq dto){

        DeliveryPartyRoom deliveryPartyRoom = dto.toEntity();
        //엔티티 조회
        Member participant = memberRepository.findById(dto.getParticipantId())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));

        DeliveryParty party= deliveryPartyRepository.findById(dto.getPartyId())
        .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTY));

        deliveryPartyRoom.connectParticipant(participant);
        deliveryPartyRoom.connectParty(party);

        deliveryPartyRoomRepository.save(deliveryPartyRoom);
        return deliveryPartyRoom;
    }
}
