package shop.geeksasang.service.common;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.domain.chat.PartyChatRoom;
import shop.geeksasang.domain.chat.PartyChatRoomMember;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.repository.chat.PartyChatRoomMemberRepository;
import shop.geeksasang.repository.chat.PartyChatRoomRepository;
import shop.geeksasang.repository.member.MemberRepository;
import shop.geeksasang.utils.fcm.FcmMessage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;

@Component
@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseCloudMessageService {
    private final MemberRepository memberRepository;
    private final PartyChatRoomRepository partyChatRoomRepository;
    private final PartyChatRoomMemberRepository partyChatRoomMemberRepository;

    public void sendDeliveryComplicatedMessage(String roomId) throws IOException, ExecutionException,InterruptedException {

        PartyChatRoom partyChatRoom = partyChatRoomRepository.findByPartyChatRoomId(new ObjectId(roomId))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_CHAT_ROOM));

        String chiefId = partyChatRoom.getChief().getId();

        //참여중인 partyChatRoomMember 조회(방장제외)
        List<PartyChatRoomMember> chatRoomMembers = partyChatRoomMemberRepository.findByChatRoomIdAndIdNotEqual(new ObjectId(roomId),new ObjectId(chiefId));

        List<Member> memberList = chatRoomMembers
                .stream()
                .map(chatMember -> memberRepository.findMemberByIdAndStatus(chatMember.getMemberId()).orElseThrow(()-> new BaseException(NOT_EXIST_USER)))
                .collect(Collectors.toList());

        List<String> tokenList = memberList
                .stream()
                .filter(mem -> !mem.getFcmToken().isEmpty())
                .map(mem -> mem.getFcmToken())
                .collect(Collectors.toList());

        String title = "배달 완료";
        String body = "배달이 완료되었습니다!";
        //String image = "https://geeksasaeng-s3.s3.ap-northeast-2.amazonaws.com/logo-14.png";

        //todo : fcm 유효하지 않을 때 다음 로직
        for( String token : tokenList ){
            try{
                sendMessageTo(token,title,body);
            }
            catch(FirebaseMessagingException e){
                log.error("cannot send to memberList push message. error info : {}", e.getMessage());
                throw new BaseException(BaseResponseStatus.INVALID_FCMTOKEN);
            }
        }
    }
    public void sendMessageTo(String targetToken, String title, String body) throws FirebaseMessagingException, IOException, InterruptedException, ExecutionException {

        //FirebaseMessaging 사용
        Message message = makeMessage(targetToken, title, body);
        String response = FirebaseMessaging.getInstance().send(message);
        log.info(response);

        //비동기
        String asyncMessage = FirebaseMessaging.getInstance().sendAsync(message).get();
        System.out.println(asyncMessage);
    }
    private Message makeMessage(String targetToken, String title, String body) {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(Message.builder()
                        .setToken(targetToken)
                        .setNotification(new Notification(title, body))
                        .build())
                .validateOnly(false)
                .build();

        return fcmMessage.getMessage();
    }
}
