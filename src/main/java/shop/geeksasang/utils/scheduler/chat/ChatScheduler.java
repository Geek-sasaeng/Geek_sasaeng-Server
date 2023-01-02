package shop.geeksasang.utils.scheduler.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatScheduler {

    private final ChatSchedulerService chatSchedulerService;

    //채팅방 24시간 후 종료 처리
    @Scheduled(cron = "0 0 1 * * *")
    public void finishChatRooms() {
        chatSchedulerService.finishChatRooms();
    }
}
