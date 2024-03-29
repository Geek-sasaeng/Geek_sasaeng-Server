package shop.geeksasang.dto.chat;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.domain.chat.Chat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class PostChatImageRes {

    private String chatId;

    private String content;

    @NotEmpty
    private String chatRoomId;

    private Boolean isSystemMessage;

    private int memberId;

    private String nickName;

    private String profileImgUrl;

    private List<Integer> readMembers = new ArrayList<>(); // 읽은 멤버 ID 리스트

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private String chatType;

    private int unreadMemberCnt;

    private Boolean isImageMessage;

    @Builder
    public PostChatImageRes(String chatId, String content, String chatRoomId, Boolean isSystemMessage, int memberId, String nickName, String profileImgUrl, List<Integer> readMembers, LocalDateTime createdAt, String chatType, int unreadMemberCnt, Boolean isImageMessage) {
        this.chatId = chatId;
        this.content = content;
        this.chatRoomId = chatRoomId;
        this.isSystemMessage = isSystemMessage;
        this.memberId = memberId;
        this.nickName = nickName;
        this.profileImgUrl = profileImgUrl;
        this.readMembers = readMembers;
        this.createdAt = createdAt;
        this.chatType = chatType;
        this.unreadMemberCnt = unreadMemberCnt;
        this.isImageMessage = isImageMessage;
    }

    public static PostChatImageRes toDto(Chat chat, String nickName, String chatType, int unreadMemberCnt){
        return PostChatImageRes.builder()
                .chatId(chat.getId())
                .content(chat.getContent())
                .chatRoomId(chat.getPartyChatRoom().getId())
                .isSystemMessage(chat.getIsSystemMessage())
                .memberId(chat.getPartyChatRoomMember().getMemberId())
                .nickName(nickName)
                .profileImgUrl(chat.getProfileImgUrl())
                .readMembers(chat.getReadMembers())
                .createdAt(chat.getBaseEntityMongo().getCreatedAt())
                .chatType(chatType)
                .unreadMemberCnt(unreadMemberCnt)
                .isImageMessage(chat.getIsImageMessage())
                .build();
    }
}
