package shop.geeksasang.service.deliveryparty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.domain.deliveryparty.DeliveryParty;
import shop.geeksasang.domain.deliveryparty.DeliveryPartyMember;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.dto.deliveryPartyMember.patch.PatchLeaveMemberReq;
import shop.geeksasang.dto.deliveryPartyMember.post.PostDeliveryPartyMemberReq;
import shop.geeksasang.dto.deliveryPartyMember.post.PostDeliveryPartyMemberRes;
import shop.geeksasang.dto.deliveryPartyMember.patch.PatchAccountTransferStatusReq;
import shop.geeksasang.dto.deliveryPartyMember.patch.PatchAccountTransferStatusRes;

import shop.geeksasang.repository.deliveryparty.DeliveryPartyRepository;
import shop.geeksasang.repository.deliveryparty.DeliveryPartyMemberRepository;
import shop.geeksasang.repository.member.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;

import static shop.geeksasang.config.TransactionManagerConfig.JPA_TRANSACTION_MANAGER;
import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class DeliveryPartyMemberService {
    private final DeliveryPartyMemberRepository deliveryPartyMemberRepository;
    private final MemberRepository memberRepository;
    private final DeliveryPartyRepository deliveryPartyRepository;

    // 파티 들어가기 - 멤버 생성
    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
    public PostDeliveryPartyMemberRes joinDeliveryPartyMember(Integer partyId, int userId){

        //엔티티 조회
        Member participant = memberRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));

        // 매칭 시 중복 validation
        if(deliveryPartyMemberRepository.findDeliveryPartyMemberByMemberIdAndDeliveryPartyId(userId, partyId).isPresent()) {
            throw new BaseException(ALREADY_PARTICIPATE_PARTY);
        }

        DeliveryParty party = deliveryPartyRepository.findDeliveryPartyById(partyId)
                .orElseThrow(() -> new BaseException(CAN_NOT_PARTICIPATE));

        // orderTime전에만 신청 가능
        if(party.getOrderTime().isBefore(LocalDateTime.now())){
            throw new BaseException(ORDER_TIME_OVER);
        }

        // maxMatching 꽉차있으면 신청X
        if(party.getMaxMatching() == party.getCurrentMatching()){
            throw new BaseException(MATCHING_COMPLITED);
        }

        DeliveryPartyMember deliveryPartyMember = new DeliveryPartyMember(participant, party);
        // 송금완료 상태 default값이 N
        deliveryPartyMember.changeAccountTransferStatusToN();

        //currentMatching 올라감.
        party.addCurrentMatching();

        // currentMatching과 maxMatching 같아지면 matching status바꿈.
        if(party.getCurrentMatching() == party.getMaxMatching()){
            party.changeMatchingStatusToFinish();
        }
        deliveryPartyMemberRepository.save(deliveryPartyMember);
        return PostDeliveryPartyMemberRes.toDto(deliveryPartyMember);
    }

    //파티(채팅방) 나오기 - 방장x
    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
    public String patchDeliveryPartyMemberStatus(Integer partyId, int memberId){
        //요청 보낸 사용자 Member
        Member findMember = memberRepository.findMemberByIdAndStatus(memberId).
                orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

        //id를 이용해 파티 조회
        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyByIdAndStatus(partyId).
                orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTY));

        //파티 멤버 조회
        DeliveryPartyMember deliveryPartyMember = deliveryPartyMemberRepository
                .findDeliveryPartyMemberByMemberIdAndDeliveryPartyId(findMember.getId(), deliveryParty.getId())
                .orElseThrow(()-> new BaseException(NOT_EXISTS_PARTY_MEMBER));

        deliveryParty.removeDeliveryPartyMember(deliveryPartyMember);
        return String.valueOf(BaseResponseStatus.LEAVE_CHATROOM_SUCCESS.getMessage());
    }


    // 수정: 송금 완료상태 수정
    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
    public PatchAccountTransferStatusRes updateAccountTransferStatus(PatchAccountTransferStatusReq dto, int memberId){
        // 멤버 조회
        DeliveryPartyMember deliveryPartyMember = deliveryPartyMemberRepository.findDeliveryPartyMemberByMemberIdAndDeliveryPartyId(memberId, dto.getPartyId())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));
        // 송금 완료상태 수정
        deliveryPartyMember.changeAccountTransferStatusToY();
        // dto형태로 병경해서 반환
        return PatchAccountTransferStatusRes.toDto(deliveryPartyMember);
    }

    //수정: 송금 완료 상태 수정2 - DeliveryPartyChatMember에서 사용
    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
    public void changeAccountTransferStatus(int partyId, int memberId){
        //배달파티 멤버 조회
        DeliveryPartyMember deliveryPartyMember = deliveryPartyMemberRepository.findDeliveryPartyMemberByMemberIdAndDeliveryPartyId(memberId, partyId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));

        // 송금 완료상태 수정
        deliveryPartyMember.changeAccountTransferStatusToY();
    }

    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
    public void forceOutMembersByChief(Integer partyId, List<Integer> membersId, int userId) {
        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyByIdAndStatus(partyId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTY));

        Member member = memberRepository.findMemberByIdAndStatus(userId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        if(deliveryParty.isNotChief(member)){
            throw new BaseException(INVALID_DELIVERY_PARTY_CHIEF);
        }

        membersId.forEach((memberId) -> {
            this.patchDeliveryPartyMemberStatus(deliveryParty.getId(), memberId);
        });
    }
}
