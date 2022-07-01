package shop.geeksasang.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.DeliveryPartyRoom;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.CreateMemberReq;
import shop.geeksasang.dto.deliveryPartyRoom.PostDeliveryPartyRoomReq;
import shop.geeksasang.repository.DeliveryPartyRoomRepository;
import shop.geeksasang.repository.MemberRepository;

import static shop.geeksasang.config.exception.BaseResponseStatus.*;

@Transactional
@Service
@RequiredArgsConstructor
public class DeliveryPartyRoomService {
    private final DeliveryPartyRoomRepository deliveryPartyRoomRepository;
    private final MemberRepository memberRepository;
    //private final DeliveryPartyRepository deliveryPartyRepository;

    @Transactional(readOnly = false)
    public DeliveryPartyRoom joinDeliveryPartyRoom(PostDeliveryPartyRoomReq dto){

        DeliveryPartyRoom deliveryPartyRoom = dto.toEntity();
        //엔티티 조회
        Member participant = memberRepository.findById(dto.getParticipant())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));

//        DeliveryParty party= deliveryPartyRepository.find(dto.getParty())
//        .orElseThrow(() -> new BaseException(NOT_EXISTSㄴ_PARTY));

        //추후 이거 삭제하고 위에 주석 풀어서 사용.
        DeliveryParty party= new DeliveryParty();

        deliveryPartyRoom.connectParticipant(participant);
        deliveryPartyRoom.connectParty(party);

        deliveryPartyRoomRepository.save(deliveryPartyRoom);

        return deliveryPartyRoom;
    }
}
