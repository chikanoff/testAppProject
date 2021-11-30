package by.itransition.chikanoff.payloads.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtResponse {
    private String token;
}
