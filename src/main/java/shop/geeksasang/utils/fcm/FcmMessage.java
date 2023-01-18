package shop.geeksasang.utils.fcm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.client.util.Key;
import com.google.firebase.messaging.Message;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FcmMessage {

    @Key("validate_only")
    @JsonIgnore
    private boolean validateOnly;

    // Notification => title(String), body(String)

    @Key("message")
    private Message message;

    @Builder
    public FcmMessage(boolean validateOnly, Message message) {
        this.validateOnly = validateOnly;
        this.message = message;
    }
}