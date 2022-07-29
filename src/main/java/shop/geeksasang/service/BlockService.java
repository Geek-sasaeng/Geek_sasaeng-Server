package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.Block;
import shop.geeksasang.domain.Member;
import shop.geeksasang.repository.BlockRepository;
import shop.geeksasang.repository.MemberRepository;

import java.util.Optional;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.NOT_EXIST_USER;


@Service
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;
    private final MemberRepository memberRepository;

    public void block(int blockingMemberId, int blockedMemberId){

        Member blockingMember = memberRepository.findMemberByIdAndStatus(blockingMemberId).orElseThrow(() -> new BaseException(NOT_EXIST_USER));
        Member blockedMember = memberRepository.findMemberByIdAndStatus(blockedMemberId).orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        //이미 신고한 사람인지 체크
        Optional<Block> checkBlock = blockRepository.findBlockByBlockedMemberAndBlockingMember(blockingMemberId, blockedMemberId);
        if(checkBlock.isPresent()){
            return;
        }

        Block block = new Block(blockingMember, blockedMember);
        blockRepository.save(block);
    }
}
