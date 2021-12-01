package by.itransition.chikanoff.service;

import by.itransition.chikanoff.IntegrationTestBase;
import by.itransition.chikanoff.beans.User;
import by.itransition.chikanoff.jwt.JwtUtils;
import by.itransition.chikanoff.payloads.response.JwtResponse;
import by.itransition.chikanoff.services.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class AuthServiceTest extends IntegrationTestBase {
    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void loginReturnsJwtResponse() {
        User user = createTestUser();
        JwtResponse response = authService.login(user.getUsername(), "password");
        assertThat(jwtUtils.getUserNameFromJwtToken(response.getToken())).isEqualTo(user.getUsername());
    }
}
