package by.itransition.chikanoff.payloads.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private @Getter @Setter
    String username;

    @NotBlank
    private @Getter @Setter
    String password;

}
