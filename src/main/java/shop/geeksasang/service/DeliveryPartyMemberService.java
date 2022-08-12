package shop.geeksasang.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.DeliveryPartyMember;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.deliveryPartyMember.patch.PatchLeaveMemberReq;
import shop.geeksasang.dto.deliveryPartyMember.post.PostDeliveryPartyMemberReq;
import shop.geeksasang.dto.deliveryPartyMember.post.PostDeliveryPartyMemberRes;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.repository.DeliveryPartyRepository;
import shop.geeksasang.repository.DeliveryPartyMemberRepository;
import shop.geeksasang.repository.MemberRepository;

import java.time.LocalDateTime;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class DeliveryPartyMemberService {
    private final DeliveryPartyMemberRepository deliveryPartyMemberRepository;
    private final MemberRepository memberRepository;
    private final DeliveryPartyRepository deliveryPartyRepository;

    // 파티 들어가기 - 멤버 생성
    @Transactional(readOnly = false)
    public PostDeliveryPartyMemberRes joinDeliveryPartyMember(PostDeliveryPartyMemberReq dto, int userId){

        //엔티티 조회
        Member participant = memberRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));

        // 매칭 시 중복 validation
        if(deliveryPartyMemberRepository.findDeliveryPartyMemberByMemberIdAndDeliveryPartyId(userId, dto.getPartyId()).isPresent()) {
            throw new BaseException(ALREADY_PARTICIPATE_PARTY);
        }

        DeliveryParty party = deliveryPartyRepository.findDeliveryPartyById(dto.getPartyId())
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

        //currentMatching 올라감.
        party.addCurrentMatching();

        // currentMatching과 maxMatching 같아지면 matching status바꿈.
        if(party.getCurrentMatching() == party.getMaxMatching()){
            party.changeMatchingStatusToFinish();
        }
        deliveryPartyMemberRepository.save(deliveryPartyMember);
        return PostDeliveryPartyMemberRes.toDto(deliveryPartyMember);
    }

    //파티(채팅방) 나오기
    @Transactional(readOnly = false)
    public String patchDeliveryPartyMemberStatus(PatchLeaveMemberReq dto, JwtInfo jwtInfo){

        int chiefId = jwtInfo.getUserId();

        //요청 보낸 사용자 Member
        int memberId = jwtInfo.getUserId();
        Member findMember = memberRepository.findMemberByIdAndStatus(memberId).
                orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

        //uuid를 이용해 파티 조회
        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyByUuid(dto.getUuid()).
                orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTY));

        //파티 멤버 조회
        DeliveryPartyMember deliveryPartyMember = deliveryPartyMemberRepository.findDeliveryPartyMemberByMemberIdAndDeliveryPartyId(findMember.getId(), deliveryParty.getId())
                .orElseThrow(()-> new BaseException(NOT_EXISTS_PARTY_MEMBER));

        //참여정보 STATUS 수정(ACTIVE -> INACTIVE)
        deliveryPartyMember.changeStatusToInactive();

        String result = String.valueOf(BaseResponseStatus.LEAVE_CHATROOM_SUCCESS.getMessage());
      // PatchLeaveMemberRes res = new PatchLeaveMemberRes(result);
        return result;
    }

}
