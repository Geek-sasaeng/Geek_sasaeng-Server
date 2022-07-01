package shop.geeksasang.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.DeliveryPartyRoom;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.CreateMemberReq;
import shop.geeksasang.dto.deliveryPartyRoom.PostDeliveryPartyRoomReq;
import shop.geeksasang.repository.DeliveryPartyRoomRepository;
import shop.geeksasang.repository.MemberRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class DeliveryPartyRoomService {
    private final DeliveryPartyRoomRepository deliveryPartyRoomRepository;
    private final MemberRepository memberRepository;
    //private final DeliveryPartyRepository deliveryPartyRepository;

    @Transactional(readOnly = false)
    public DeliveryPartyRoom joinDeliveryPartyRoom(PostDeliveryPartyRoomReq dto){

        //엔티티 조회
        //Member participant = memberRepository.find();
        //DeliveryParty deliveryParty= deliveryPartyRepository.find();

        DeliveryPartyRoom deliveryPartyRoom = dto.toEntity();

        deliveryPartyRoomRepository.save(deliveryPartyRoom);

        return deliveryPartyRoom;
    }
}
