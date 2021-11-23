package by.itransition.chikanoff.payloads.response;

import lombok.Getter;
import lombok.Setter;

public class MessageResponse {
    private @Getter @Setter
    String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
