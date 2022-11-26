package shop.geeksasang.service.chat;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import shop.geeksasang.domain.deliveryparty.DeliveryPartyMember;
import shop.geeksasang.dto.chatroom.GetChatRoomsRes;
import shop.geeksasang.repository.deliveryparty.DeliveryPartyMemberRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final DeliveryPartyMemberRepository deliveryPartyMemberRepository;

    public List<GetChatRoomsRes> getChatRooms(int userId){
        List<DeliveryPartyMember> partyMembers = deliveryPartyMemberRepository.findByPartiesByDeliveryPartyMemberId(userId);

        return partyMembers.stream()
                .map(partyMember -> new GetChatRoomsRes(partyMember.getParty().getUuid()))
                .collect(Collectors.toList());
    }
}
