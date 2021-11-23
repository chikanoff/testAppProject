package by.itransition.chikanoff.payloads.response;

import lombok.Getter;
import lombok.Setter;

public class JwtResponse {
    private @Getter @Setter
    String token;
    private @Getter @Setter
    String type = "Bearer";
    private @Getter @Setter
    Long id;
    private @Getter @Setter
    String fullName;
    private @Getter @Setter
    String username;
    private @Getter @Setter
    String email;

    public JwtResponse(String accessToken, Long id, String fullName, String username, String email) {
        this.token = accessToken;
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
    }
}
