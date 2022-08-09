package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.chatroom.GetChatRoomsRes;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.service.ChatRoomService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/chat-rooms")
    public BaseResponse<List<GetChatRoomsRes> > getChatRooms(HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        List<GetChatRoomsRes> chatRooms = chatRoomService.getChatRooms(jwtInfo.getUserId());
        return new BaseResponse<>(chatRooms);
    }
}
