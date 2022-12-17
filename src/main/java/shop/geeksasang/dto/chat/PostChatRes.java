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
public class PostChatRes {

    private String chatId;

    @NotEmpty
    private String content;

    @NotEmpty
    private String chatRoomId;

    private Boolean isSystemMessage;

    private int memberId;

    private String email;

    private String profileImgUrl;

    private List<Integer> readMembers = new ArrayList<>(); // 읽은 멤버 ID 리스트

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private String chatType;

    private int unreadMemberCnt;

    private Boolean isImageMessage;

    public PostChatRes(String chatRoomId, String content, LocalDateTime createdAt, int memberId) {
        this.chatRoomId = chatRoomId;
        this.content = content;
        this.createdAt = createdAt;
        this.memberId = memberId;
    }

    public PostChatRes(String email, String chatRoomId, String content, LocalDateTime createdAt, int memberId) {
        this.email = email;
        this.chatRoomId = chatRoomId;
        this.content = content;
        this.createdAt = createdAt;
        this.memberId = memberId;
    }

    @Builder
    public PostChatRes(String chatId, String content, String chatRoomId, Boolean isSystemMessage, int memberId, String email, String profileImgUrl, List<Integer> readMembers, LocalDateTime createdAt, String chatType, int unreadMemberCnt, Boolean isImageMessage) {
        this.chatId = chatId;
        this.content = content;
        this.chatRoomId = chatRoomId;
        this.isSystemMessage = isSystemMessage;
        this.memberId = memberId;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.readMembers = readMembers;
        this.createdAt = createdAt;
        this.chatType = chatType;
        this.isImageMessage = isImageMessage;
        this.unreadMemberCnt = unreadMemberCnt;
    }

    public static PostChatRes toDto(Chat chat, String email, String chatType, int unreadMemberCnt){
        return PostChatRes.builder()
                .chatId(chat.getId())
                .content(chat.getContent())
                .chatRoomId(chat.getPartyChatRoom().getId())
                .isSystemMessage(chat.getIsSystemMessage())
                .memberId(chat.getPartyChatRoomMember().getMemberId())
                .email(email)
                .profileImgUrl(chat.getProfileImgUrl())
                .readMembers(chat.getReadMembers())
                .createdAt(chat.getBaseEntityMongo().getCreatedAt())
                .chatType(chatType)
                .unreadMemberCnt(unreadMemberCnt)
                .isImageMessage(chat.getIsImageMessage())
                .build();
    }
}
