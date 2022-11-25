package shop.geeksasang.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import shop.geeksasang.dto.chatting.PostChattingRes;
import shop.geeksasang.service.DeliveryPartyChattingService;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class SocketHandler extends TextWebSocketHandler {

    private final RabbitTemplate rabbitTemplate;
    private final String EXCHANGE = "chatting-room-exchange-test2";
    private final DeliveryPartyChattingService deliveryPartyChattingService;

    HashMap<String, WebSocketSession> sessionMap = new HashMap<>(); //웹소켓 세션을 담아둘 맵

    /**
     * 메시지 발송
     * @author 토마스, 네오
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String msg = message.getPayload();
        System.out.println("전송하는 웹소켓 메시지="+msg);

        try {
            // json 형식으로 변환 후 전송
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            PostChattingRes postChattingRes = mapper.readValue(msg, PostChattingRes.class);

            deliveryPartyChattingService.createChatting(1, postChattingRes.getEmail(), postChattingRes.getChattingRoomId(), postChattingRes.getContent());//TODO: userId 넣는 부분 멤버 엔티티 구현 후 수정
        } catch (Exception e) {
            System.out.println("웹소켓 메시지 전송 에러 발생");
        }
    }

    /**
     * 소켓 연결
     * @author 토마스, 네오
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        super.afterConnectionEstablished(session);
        sessionMap.put(session.getId(), session);
        System.out.println("connect");
    }

    /**
     * 소켓 종료
     * @author 토마스, 네오
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        sessionMap.remove(session.getId());
        super.afterConnectionClosed(session, status);
    }

    /**
     *
     * @author 토마스, 네오
     */
    public String getRoutingKey(String queueName) {
        String[] routingKey = queueName.split("\\.");

        StringBuilder sb = new StringBuilder();

        sb.append(routingKey[0]);
        sb.append(".");
        sb.append(routingKey[1]);
        sb.append(".*");
        return sb.toString();
    }
}
