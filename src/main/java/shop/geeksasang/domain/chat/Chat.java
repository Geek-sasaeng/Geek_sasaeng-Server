package shop.geeksasang.domain.chat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.type.ChatType;
import shop.geeksasang.domain.Member;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Chat extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "chat_id")
    private int id;

    private String content;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member sender;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="chat_room_id")
    private ChatRoom chatRoom;

    @Enumerated
    private ChatType chatType;

    @Builder
    public Chat(String content, Member sender, ChatRoom chatRoom, ChatType chatType) {
        this.content = content;
        this.sender = sender;
        this.chatRoom = chatRoom;
        this.chatType = chatType;
    }
}
