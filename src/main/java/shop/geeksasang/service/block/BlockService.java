package shop.geeksasang.service.block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.block.Block;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.repository.block.BlockRepository;
import shop.geeksasang.repository.member.MemberRepository;

import java.util.Optional;

import static shop.geeksasang.config.TransactionManagerConfig.JPA_TRANSACTION_MANAGER;
import static shop.geeksasang.config.exception.response.BaseResponseStatus.NOT_EXIST_USER;


@Service
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
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
