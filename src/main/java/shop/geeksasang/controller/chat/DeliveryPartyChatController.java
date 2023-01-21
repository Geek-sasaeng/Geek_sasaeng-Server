package shop.geeksasang.controller.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.chat.Chat;

import shop.geeksasang.dto.SuccessCommonRes;
import shop.geeksasang.dto.chat.chatchief.DeleteMemberByChiefReq;
import shop.geeksasang.dto.chat.chatchief.PatchChiefReq;
import shop.geeksasang.dto.chat.chatmember.*;

import shop.geeksasang.dto.chat.PostChatImageReq;


import shop.geeksasang.dto.chat.partychatroom.PatchOrderReq;
import shop.geeksasang.dto.chat.partychatroom.GetPartyChatRoomDetailRes;
import shop.geeksasang.dto.chat.partychatroom.GetPartyChatRoomsRes;
import shop.geeksasang.dto.chat.PostChatReq;
import shop.geeksasang.dto.chat.partychatroom.PartyChatRoomRes;
import shop.geeksasang.dto.chat.partychatroom.post.PostPartyChatRoomReq;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.service.chat.DeliveryPartyChatService;
import shop.geeksasang.service.common.FirebaseCloudMessageService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/party-chat-room")
@RequiredArgsConstructor
public class DeliveryPartyChatController {

    private final DeliveryPartyChatService deliveryPartyChatService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @ApiOperation(value = "채팅방 생성", notes = "(jwt 토큰 필요)배달 채팅방 생성 요청")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009, message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @PostMapping
    public BaseResponse<PartyChatRoomRes> createPartyChatRoom(HttpServletRequest request, @RequestBody @Validated PostPartyChatRoomReq dto){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        PartyChatRoomRes res = deliveryPartyChatService.createChatRoom(jwtInfo.getUserId(), dto.getTitle(), dto.getAccountNumber(), dto.getBank(), dto.getCategory(), dto.getMaxMatching(), dto.getDeliveryPartyId());
        return new BaseResponse<>(res);
    }

    @PostMapping("/chat")
    public BaseResponse<String> createChat(HttpServletRequest request, @RequestBody PostChatReq dto){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        deliveryPartyChatService.createChat(jwtInfo.getUserId(), dto.getChatRoomId(), dto.getContent(), dto.getIsSystemMessage(), dto.getProfileImgUrl(), dto.getChatType(), dto.getChatId(), false);
        return new BaseResponse("채팅송신을 성공했습니다.");
    }


    @ApiOperation(value = "채팅 이미지 전송", notes = "채팅방에서 이미지 전송 API. swagger 에서 이미지(multipartfile)처리가 잘 되지 않으므로, postman으로 테스트 바랍니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", required = true, dataTypeClass = String.class, example = "이미지 채팅입니다."),
            @ApiImplicitParam(name = "chatRoomId", required = true, dataTypeClass = String.class, example = "638ef5c109c1212827135cf3"),
            @ApiImplicitParam(name = "isSystemMessage", dataTypeClass = Boolean.class, example = "false"),
            //@ApiImplicitParam(name = "email", required = true, dataTypeClass = String.class, example = "thomas@gmail.com"),
            @ApiImplicitParam(name = "profileImgUrl", required = true, dataTypeClass = String.class, example = "https://yogit.s3.ap-northeast-2.amazonaws.com/boardimguuid2"),
            @ApiImplicitParam(name = "chatType", required = true, dataTypeClass = String.class, example = "publish 또는 read"),
            @ApiImplicitParam(name = "chatId", required = true, dataTypeClass = String.class, example = "publish일 때는 none / read일 때는 chatId(인덱스)"),
            @ApiImplicitParam(name = "images", required = true, dataTypeClass = MultipartFile.class, example = "MultiPartFile 클래스 스웨거에서 에러나면 포스트맨에서 실행 바람"),
            @ApiImplicitParam(name = "isImageMessage", dataTypeClass = Boolean.class, example = "false"),
    })
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009, message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 2207, message ="채팅방이 존재하지 않습니다."),
            @ApiResponse(code = 2208, message ="채팅방 멤버가 존재하지 않습니다."),
            @ApiResponse(code = 2211, message ="이미지 갯수를 초과했습니다."),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @PostMapping("/chatimage")
    public BaseResponse<String> createChatImage(HttpServletRequest request, @ModelAttribute @Validated PostChatImageReq dto){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        deliveryPartyChatService.createChatImage(jwtInfo.getUserId(), dto.getChatRoomId(), dto.getContent(), dto.getIsSystemMessage(), dto.getProfileImgUrl(), dto.getChatType(), dto.getChatId(), dto.getImages(), dto.getIsImageMessage());
        return new BaseResponse("채팅 이미지 전송을 성공했습니다.");
    }


    @ApiOperation(value = "채팅방 멤버 추가 및 rabbitmq 큐 생성", notes = "(jwt 토큰 필요)멤버의 정보만 추가")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009, message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 2005, message ="채팅방이 존재하지 않습니다."),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @PostMapping("/member")
    public BaseResponse<PartyChatRoomMemberRes> joinPartyChatRoom(HttpServletRequest request, @RequestBody PostPartyChatRoomMemberReq dto){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        PartyChatRoomMemberRes res = deliveryPartyChatService.joinPartyChatRoom(jwtInfo.getUserId(), dto.getPartyChatRoomId(), LocalDateTime.now());
        return new BaseResponse<>(res);
    }


    @ApiOperation(value = "채팅방 전체 조회", notes = "(jwt 토큰 필요)전체 조회")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
//          @ApiResponse(code = 2009, message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @GetMapping
    public BaseResponse<GetPartyChatRoomsRes> findPartyChatRooms(HttpServletRequest request, @RequestParam int cursor){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        return new BaseResponse(deliveryPartyChatService.findPartyChatRooms(jwtInfo.getUserId(), cursor));
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
        return new BaseResponse(deliveryPartyChatService.findPartyChattings(jwtInfo.getUserId(), partyChatRoomId));
    }


    @ApiOperation(value = "방장이 배달 파티 채팅 멤버를 강제퇴장", notes = "(jwt 토큰 필요)전체 조회")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2024 ,message ="배달 파티 채팅방 방장이 존재하지 않습니다."),
            @ApiResponse(code = 2025 ,message ="배달 파티 채팅방 방장이 아닙니다."),
            @ApiResponse(code = 2026 ,message ="송금을 완료한 멤버는 방에서 퇴장시킬 수 없습니다."),
            @ApiResponse(code = 2208 ,message ="채팅방 멤버가 존재하지 않습니다."),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @DeleteMapping("/members")
    public BaseResponse<SuccessCommonRes> removeChatRoomMemberByChief(HttpServletRequest request, @Valid @RequestBody DeleteMemberByChiefReq dto){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        deliveryPartyChatService.removeMemberByChief(jwtInfo.getUserId(), dto);
        return new BaseResponse(new SuccessCommonRes());
    }

    @ApiOperation(value = "방장 퇴장", notes = "(jwt 토큰 필요)전체 조회")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2024 ,message ="배달 파티 채팅방 방장이 존재하지 않습니다."),
            @ApiResponse(code = 2208 ,message ="채팅방 멤버가 존재하지 않습니다."),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @PatchMapping("/chief")
    public BaseResponse<SuccessCommonRes> changeChief(HttpServletRequest request, @Valid @RequestBody PatchChiefReq dto){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        deliveryPartyChatService.changeChief(jwtInfo.getUserId(), dto.getRoomId());
        return new BaseResponse(new SuccessCommonRes());
    }

    @ApiOperation(value = "배달 파티 채팅 멤버가 스스로 퇴장", notes = "(jwt 토큰 필요)전체 조회")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2008 ,message ="채팅방 멤버가 존재하지 않습니다."),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @DeleteMapping("/members/self")
    public BaseResponse<SuccessCommonRes> removeChatRoomMember(HttpServletRequest request, @Valid @RequestBody PatchMemberReq dto) throws JsonProcessingException {
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        deliveryPartyChatService.removeMember(jwtInfo.getUserId(), dto.getRoomId());
        return new BaseResponse(new SuccessCommonRes());
    }

    @ApiOperation(value = "송금 완료 ", notes = "(jwt 토큰 필요) 채팅방 내에서 송금완료 버튼을 누를 때 사용하는 api")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2208 ,message ="채팅방 멤버가 존재하지 않습니다."),
            @ApiResponse(code = 2009 ,message ="존재하지 않는 멤버입니다."),
            @ApiResponse(code = 2207 ,message ="채팅방이 존재하지 않습니다."),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @PatchMapping("/members/remittance")
    public BaseResponse<String> changeRemittance(HttpServletRequest request, @Valid @RequestBody PatchRemittanceReq dto){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        deliveryPartyChatService.changeRemittance(jwtInfo.getUserId(), dto.getRoomId());
        return new BaseResponse<>("요청에 성공하셨습니다.");
    }

    @ApiOperation(value = "주문 완료 ", notes = "(jwt 토큰 필요) 채팅방 내에서 주문 완료 버튼을 누를 때 사용하는 api")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2208 ,message ="채팅방 멤버가 존재하지 않습니다."),
            @ApiResponse(code = 2009 ,message ="존재하지 않는 멤버입니다."),
            @ApiResponse(code = 2207 ,message ="채팅방이 존재하지 않습니다."),
            @ApiResponse(code = 2010 ,message ="존재하지 않는 파티입니다."),
            @ApiResponse(code = 2210 ,message ="채팅이 존재하지 않습니다."),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다."),
    })
    @PatchMapping("/order")
    public BaseResponse<String> changeOrderStatus(HttpServletRequest request, @Valid @RequestBody PatchOrderReq dto){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        deliveryPartyChatService.changeOrderStatus(jwtInfo.getUserId(), dto.getRoomId());
        return new BaseResponse<>("요청에 성공하셨습니다.");
    }

    @ApiOperation(value = "채팅방 상세조회", notes = "(jwt 토큰 필요) 채팅방 상세 조회시 사용하는 api")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2208 ,message ="채팅방 멤버가 존재하지 않습니다."),
            @ApiResponse(code = 2207 ,message ="채팅방이 존재하지 않습니다."),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다."),
    })
    @GetMapping("/{chatRoomId}")
    public BaseResponse<GetPartyChatRoomDetailRes> getPartyChatRoomDetailById(@PathVariable("chatRoomId")String chatRoomId, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        GetPartyChatRoomDetailRes response = deliveryPartyChatService.getPartyChatRoomDetailById(chatRoomId, jwtInfo.getUserId());
        return new BaseResponse<>(response);
    }

    @ApiOperation(value = "배달 완료", notes = "(jwt 토큰 필요) 배달 완료시 fcm 푸시 알림을 보내주는 api")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2208 ,message ="채팅방 멤버가 존재하지 않습니다."),
            @ApiResponse(code = 2009 ,message ="존재하지 않는 멤버입니다."),
            @ApiResponse(code = 2207 ,message ="채팅방이 존재하지 않습니다."),
            @ApiResponse(code = 2010 ,message ="존재하지 않는 파티입니다."),
            @ApiResponse(code = 2617 ,message ="배달 완료가 불가능한 유저입니다."),
            @ApiResponse(code = 2408 ,message ="유효하지 않은 fcm 토큰입니다."),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @PatchMapping("/delivery-complete")
    public BaseResponse<String> changeDeliveryComplete(HttpServletRequest request, @Valid @RequestBody PatchOrderReq dto) throws IOException, ExecutionException,InterruptedException {
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        deliveryPartyChatService.changeDeliveryComplete(jwtInfo.getUserId(), dto.getRoomId());
        firebaseCloudMessageService.sendDeliveryComplicatedMessage(dto.getRoomId());

        return new BaseResponse<>("요청에 성공하셨습니다.");
    }


}
