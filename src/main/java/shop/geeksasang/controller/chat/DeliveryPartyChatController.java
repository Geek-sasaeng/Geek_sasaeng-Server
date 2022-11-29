package shop.geeksasang.controller.chat;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.chat.Chat;
import shop.geeksasang.domain.chat.ChatRoom;
import shop.geeksasang.dto.chat.partychatroom.GetPartyChatRoomsRes;
import shop.geeksasang.dto.chat.PostChattingReq;
import shop.geeksasang.dto.chat.chatmember.PostPartyChatRoomMemberReq;
import shop.geeksasang.dto.chat.partyChatRoom.PostPartyChatRoomReq;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.service.chat.DeliveryPartyChatService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/party-chat-room")
@RequiredArgsConstructor
public class DeliveryPartyChatController {

    private final DeliveryPartyChatService deliveryPartyChattingService;

    @ApiOperation(value = "채팅방 생성", notes = "(jwt 토큰 필요)마이페이지-공지사항에서 공지사항을 전체 조회")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009, message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @PostMapping
    public BaseResponse<Long> createPartyChatRoom(HttpServletRequest request, @RequestBody @Validated PostPartyChatRoomReq dto){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        String id = deliveryPartyChattingService.createChatRoom(jwtInfo.getUserId(), dto.getTitle());
        return new BaseResponse(id);
    }

    @PostMapping("/chat")
    public BaseResponse<String> createPartyChatting(HttpServletRequest request, @RequestBody PostChattingReq dto){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        deliveryPartyChattingService.createChat(jwtInfo.getUserId(), dto.getEmail(), dto.getChatRoomId(), dto.getContent(), dto.getIsSystemMessage(), dto.getProfileImgUrl());
        return new BaseResponse("채팅송신을 성공했습니다.");
    }


    @ApiOperation(value = "채팅방 멤버 추가", notes = "(jwt 토큰 필요)멤버의 정보만 추가")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009, message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 2005, message ="채팅방이 존재하지 않습니다."),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @PostMapping("/member")
    public BaseResponse<String> joinPartyChatRoom(HttpServletRequest request, @RequestBody PostPartyChatRoomMemberReq dto){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        deliveryPartyChattingService.joinPartyChatRoom(dto.getChatRoomId(), LocalDateTime.now(), dto.getIsRemittance(), jwtInfo.getUserId());
        return new BaseResponse("채팅방에 멤버가 추가되었습니다.");
    }


    @ApiOperation(value = "채팅방 전체 조회", notes = "(jwt 토큰 필요)전체 조회")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
//            @ApiResponse(code = 2009, message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @GetMapping
    public BaseResponse<GetPartyChatRoomsRes> findPartyChatRooms(HttpServletRequest request, @RequestParam int cursor){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        return new BaseResponse(deliveryPartyChattingService.findPartyChatRooms(jwtInfo.getUserId(), cursor));
    }


    @ApiOperation(value = "채팅방 조회", notes = "(jwt 토큰 필요)전체 조회")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009, message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @PostMapping("/get/{partyChatRoomId}")
    public BaseResponse<List<ChatRoom>> findPartyChatRoom(HttpServletRequest request, @PathVariable("partyChatRoomId") String partyChatRoomId){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        return new BaseResponse(deliveryPartyChattingService.findPartyChatRoom(jwtInfo.getUserId(), partyChatRoomId));
    }


    @ApiOperation(value = "채팅 전체 조회", notes = "(jwt 토큰 필요)전체 조회")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009, message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @PostMapping("/chatting/get/{partyChatRoomId}")
    public BaseResponse<List<Chat>> findPartyChattings(HttpServletRequest request, @PathVariable("partyChatRoomId") String partyChatRoomId){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        return new BaseResponse(deliveryPartyChattingService.findPartyChattings(jwtInfo.getUserId(), partyChatRoomId));
    }
}
