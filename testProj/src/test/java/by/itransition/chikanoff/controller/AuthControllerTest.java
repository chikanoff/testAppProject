package by.itransition.chikanoff.controller;

import by.itransition.chikanoff.beans.User;
import by.itransition.chikanoff.payloads.request.LoginRequest;
import by.itransition.chikanoff.repository.UserRepository;
import by.itransition.chikanoff.services.AuthService;
import by.itransition.chikanoff.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class AuthControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @Mock
    private AuthService authService;

    @Mock
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void signInReturnsStatusOk() throws Exception {
        User user = createTestUser();
        LoginRequest req = new LoginRequest();
        req.setUsername("user");
        req.setPassword("password");

        mvc.perform(
                post("/api/auth/signin")
                   .contentType("application/json")
                   .param("sendWelcomeMail", "true")
                   .content(objectMapper.writeValueAsString(req)))
                   .andExpect(status().isOk());
    }

    private User createTestUser(){
        return userRepository.save(new User(
                "testFullName",
                "testUsername",
                "testEmail@gmail.com",
                encoder.encode("testPassword")
        ));
    }


    @AfterEach
    public void resetDb(){
        userRepository.deleteAll();
    }
}
