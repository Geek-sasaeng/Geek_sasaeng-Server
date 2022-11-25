package shop.geeksasang.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.chatting.Chatting;
import shop.geeksasang.domain.chatting.ChattingRoom;
import shop.geeksasang.dto.chatting.PostChattingReq;
import shop.geeksasang.dto.chatting.chattingmember.PostParticipantInfoReq;
import shop.geeksasang.dto.chatting.partychattingroom.PostPartyChattingRoomReq;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.service.DeliveryPartyChattingService;
import shop.geeksasang.utils.jwt.NoIntercept;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/party-chatting-room")
@RequiredArgsConstructor
public class DeliveryPartyChattingController {

    private final DeliveryPartyChattingService deliveryPartyChattingService;

    @ApiOperation(value = "채팅방 생성", notes = "(jwt 토큰 필요)마이페이지-공지사항에서 공지사항을 전체 조회")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009, message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @PostMapping
    public BaseResponse<Long> createPartyChattingRoom(HttpServletRequest request, @RequestBody @Validated PostPartyChattingRoomReq postPartyChattingRoomReq){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        String id = deliveryPartyChattingService.createChattingRoom(jwtInfo.getUserId(), postPartyChattingRoomReq.getTitle());
        return new BaseResponse(id);
    }

    @PostMapping("/chatting")
    public BaseResponse<String> createPartyChatting(HttpServletRequest request, @RequestBody PostChattingReq dto){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        System.out.println("dto.getChattingRoomId() = " + dto.getChattingRoomId());
        deliveryPartyChattingService.createChatting(jwtInfo.getUserId(), "tomas", dto.getChattingRoomId(), dto.getContent());
        return new BaseResponse("채팅송신을 성공했습니다.");
    }
//    @NoIntercept //TODO:개발을 위해 임시로 jwt 허용되게한 것. 추후 제거 바람.
//    public BaseResponse<String> createPartyChatting(HttpServletRequest request, @RequestBody PostChattingReq dto){
////        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
//        System.out.println("dto.getChattingRoomId() = " + dto.getChattingRoomId());
//        deliveryPartyChattingService.createChatting(1, dto.getChattingRoomId(), dto.getContent(), dto.getParticipantsCnt());
//        return new BaseResponse("채팅송신을 성공했습니다.");
//    }

    /**
     * @author 토마스
     */
    @ApiOperation(value = "채팅방 멤버 추가", notes = "(jwt 토큰 필요)멤버의 정보만 추가")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009, message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 2005, message ="채팅방이 존재하지 않습니다."),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @PostMapping("/member")
    @NoIntercept //TODO:개발을 위해 임시로 jwt 허용되게한 것. 추후 제거 바람.
    public BaseResponse<String> joinPartyChattingRoom(HttpServletRequest request, @RequestBody PostParticipantInfoReq postParticipantInfoReq){
        deliveryPartyChattingService.joinPartyChattingRoom(postParticipantInfoReq.getChattingRoomId(), LocalDateTime.now(), postParticipantInfoReq.getIsRemittance(), postParticipantInfoReq.getMemberId());
        return new BaseResponse("채팅방에 멤버가 추가되었습니다.");
    }


    @ApiOperation(value = "채팅방 전체 조회", notes = "(jwt 토큰 필요)전체 조회")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009, message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @PostMapping("/get")
    public BaseResponse<List<ChattingRoom>> findAllPartyChattingRooms(HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        return new BaseResponse(deliveryPartyChattingService.findAllPartyChattingRooms(jwtInfo.getUserId()));
    }

    @ApiOperation(value = "채팅방 조회", notes = "(jwt 토큰 필요)전체 조회")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009, message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @PostMapping("/get/{partyChattingRoomId}")
    public BaseResponse<List<ChattingRoom>> findPartyChattingRoom(HttpServletRequest request, @PathVariable("partyChattingRoomId") String partyChattingRoomId){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        return new BaseResponse(deliveryPartyChattingService.findPartyChattingRoom(jwtInfo.getUserId(),partyChattingRoomId));
    }

    @ApiOperation(value = "채팅 전체 조회", notes = "(jwt 토큰 필요)전체 조회")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009, message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @PostMapping("/chatting/get/{partyChattingRoomId}")
    public BaseResponse<List<Chatting>> findPartyChattings(HttpServletRequest request, @PathVariable("partyChattingRoomId") String partyChattingRoomId){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        return new BaseResponse(deliveryPartyChattingService.findPartyChattings(jwtInfo.getUserId(), partyChattingRoomId));
    }
}
