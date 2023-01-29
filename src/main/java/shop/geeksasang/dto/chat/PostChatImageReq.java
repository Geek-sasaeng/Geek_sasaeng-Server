package shop.geeksasang.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostChatImageReq {

    private String content;

    private String chatRoomId;

    private Boolean isSystemMessage;

    private String chatType;

    private String chatId;

    private List<MultipartFile> images; // 최대 5개

    private Boolean isImageMessage;
}
