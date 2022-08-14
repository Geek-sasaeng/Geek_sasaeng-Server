package shop.geeksasang.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "조회: 배달 파티 채팅방 UUID 조회", notes = "로그인한 유저가 참여한 배달파티 채팅방의 UUID를 가져온다")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 4000 ,message = "서버 오류입니다.")
    })
    @GetMapping("/chat-rooms")
    public BaseResponse<List<GetChatRoomsRes>> getChatRooms(HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        List<GetChatRoomsRes> chatRooms = chatRoomService.getChatRooms(jwtInfo.getUserId());
        return new BaseResponse<>(chatRooms);
    }
}
