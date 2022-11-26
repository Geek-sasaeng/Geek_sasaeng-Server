package shop.geeksasang.repository.block;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.geeksasang.domain.block.Block;


import java.util.List;
import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Integer> {

    @Query("select b from Block b where b.blockingMember.id = :blockingMemberId and b.blockedMember.id = :blockedMemberId")
    Optional<Block> findBlockByBlockedMemberAndBlockingMember(int blockingMemberId, int blockedMemberId);

    @Query("select b from Block b where b.blockingMember.id = :blockingMemberId")
    List<Block>  findBlocksByBlockingMember(int blockingMemberId);
}
