package shop.geeksasang.utils.event;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.geeksasang.service.member.MemberService;

@Component
@RequiredArgsConstructor
public class DeliveryCompleteEventListener {

    private final MemberService memberService;
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void updateMemberGrade(DeliveryCompletedEvent event){
        int memberId = event.getMemberId();
        //배달완료 한 배달파티 게시글 세기. 횟수 비교. 변경해야되면 변경.
        memberService.updateMemberGrade(memberId);
    }
}
