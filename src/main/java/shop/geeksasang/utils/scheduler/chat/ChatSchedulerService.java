package shop.geeksasang.utils.scheduler.chat;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.domain.chat.PartyChatRoom;
import shop.geeksasang.repository.chat.PartyChatRoomRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatSchedulerService {

    private final PartyChatRoomRepository partyChatRoomRepository;

    @Transactional(readOnly = false)
    public void finishChatRooms(){

        List<PartyChatRoom> partyChatRooms = partyChatRoomRepository.findAll();
        if(!partyChatRooms.isEmpty()){
            for(PartyChatRoom p: partyChatRooms){
                if(p.getChats().get(p.getChats().size() - 1).getBaseEntityMongo().getUpdatedAt().isAfter(LocalDateTime.now().minusDays(1))){
                    partyChatRoomRepository.changeIsFinish(new ObjectId(p.getId()));
                }
            }
        }

    }
}
