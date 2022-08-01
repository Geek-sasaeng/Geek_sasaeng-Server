package shop.geeksasang.domain.chat;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "chat_room_type")
public abstract class ChatRoom {
    @Id
    @GeneratedValue
    @Column(name = "chat_room_id")
    private int id;

    private String title;

    @OneToMany(mappedBy = "chatRoom")
    List<Chat> chats = new ArrayList<>();

    public ChatRoom(String title) {
        this.title = title;
    }
}
