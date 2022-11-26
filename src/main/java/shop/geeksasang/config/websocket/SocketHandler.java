package shop.geeksasang.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import shop.geeksasang.dto.chat.PostChatRes;
import shop.geeksasang.service.chat.DeliveryPartyChatService;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class SocketHandler extends TextWebSocketHandler {

    private final DeliveryPartyChatService deliveryPartyChatService;

    HashMap<String, WebSocketSession> sessionMap = new HashMap<>(); //웹소켓 세션을 담아둘 맵

    // 메시지 발송
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String msg = message.getPayload();
        System.out.println("전송하는 웹소켓 메시지="+msg);

        try {
            // json 형식으로 변환 후 전송
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            PostChatRes postChatRes = mapper.readValue(msg, PostChatRes.class);

            deliveryPartyChatService.createChat(postChatRes.getMemberId(), postChatRes.getEmail(), postChatRes.getChatRoomId(), postChatRes.getContent(), postChatRes.getIsSystemMessage(), postChatRes.getProfileImgUrl());//TODO: userId 넣는 부분 멤버 엔티티 구현 후 수정
        } catch (Exception e) {
            System.out.println("웹소켓 메시지 전송 에러 발생");
            e.printStackTrace();
        }
    }

    // 소켓 연결 후
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        super.afterConnectionEstablished(session);
        sessionMap.put(session.getId(), session);
        System.out.println("웹소켓이 연결되었습니다.");
    }

    // 소켓 연결 종료 후
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        sessionMap.remove(session.getId());
        super.afterConnectionClosed(session, status);
    }

}
