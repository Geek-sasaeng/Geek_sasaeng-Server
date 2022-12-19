package shop.geeksasang.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.dto.chat.PostChatReq;
import shop.geeksasang.dto.chat.PostChatRes;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.repository.member.MemberRepository;
import shop.geeksasang.service.chat.DeliveryPartyChatService;
import shop.geeksasang.utils.jwt.JwtService;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;

@Component
@RequiredArgsConstructor
public class SocketHandler extends TextWebSocketHandler {

    private final DeliveryPartyChatService deliveryPartyChatService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Value("${secret.jwt_secret_key}")
    private String secretKey;

    HashMap<String, WebSocketSession> sessionMap = new HashMap<>(); //웹소켓 세션을 담아둘 맵

    // 메시지 발송
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String msg = message.getPayload();
        System.out.println("전송하는 웹소켓 메시지="+msg);

        try {
            // json 형식으로 변환 후 전송
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            PostChatReq dto = mapper.readValue(msg, PostChatReq.class);

            // 1.jwt 추출
            String accessToken=null;
            if(dto.getJwt().toLowerCase().startsWith("Bearer".toLowerCase())){
                accessToken = dto.getJwt().substring("Bearer".length()).trim();
                if(accessToken == null || accessToken.length() == 0){
                    throw new BaseException(EMPTY_JWT);
                }
            }
            // 2. JWT parsing
            Claims body;
            try{
                body = Jwts.parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(accessToken)
                        .getBody();
            } catch (Exception ignored) {
                throw new BaseException(INVALID_JWT);
            }

            //3. JWT 유효기간 확인
            if(!jwtService.validateToken(accessToken))
                throw new BaseException(EXPIRED_JWT);

            //LinkedHashMap 타입 jwtInfo parsings
            System.out.println("body = " + body.get("jwtInfo"));
            LinkedHashMap jwtInfo = body.get("jwtInfo", LinkedHashMap.class);

            //LinkedHashMap 타입 jwtInfo값을 JwtInfo.class 타입으로 convert
            JwtInfo convertJwtInfo = mapper.convertValue(jwtInfo,JwtInfo.class);
            int memberId = convertJwtInfo.getUserId();
            Member member = memberRepository.findMemberById(memberId)
                    .orElseThrow(() -> new BaseException(NOT_EXIST_USER));

            System.out.println("멤버 ID" + memberId + "이고 프로필 이미지는" + member.getProfileImgUrl());
            deliveryPartyChatService.createChat(memberId, dto.getChatRoomId(), dto.getContent(), dto.getIsSystemMessage(), member.getProfileImgUrl(), dto.getChatType(), dto.getChatId(), false);
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
