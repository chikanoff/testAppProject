package by.itransition.chikanoff.controller;

import by.itransition.chikanoff.IntegrationTestBase;
import by.itransition.chikanoff.beans.User;
import by.itransition.chikanoff.payloads.request.LoginRequest;
import by.itransition.chikanoff.payloads.request.SignupRequest;
import by.itransition.chikanoff.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class AuthControllerTest extends IntegrationTestBase {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void signInReturnsStatusOk() throws Exception {
        createTestUser();
        LoginRequest req = new LoginRequest();
        req.setUsername("testUsername");
        req.setPassword("password");

        mvc.perform(
                post("/api/auth/signin")
                   .contentType("application/json")
                   .param("sendWelcomeMail", "true")
                   .content(objectMapper.writeValueAsString(req)))
                   .andExpect(status().isOk());
    }

    @Test
    public void signInReturnsStatusUnauthorized() throws Exception {
        User user = createTestUser();
        LoginRequest req = new LoginRequest();
        req.setUsername("badUsername");
        req.setPassword("password");

        mvc.perform(
                        post("/api/auth/signin")
                                .contentType("application/json")
                                .param("sendWelcomeMail", "true")
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void signUpReturnsStatusOk() throws Exception {
        SignupRequest req = new SignupRequest();
        req.setFullName("testFullName");
        req.setUsername("testUsername");
        req.setEmail("email@gmail.com");
        req.setPassword("password");

        mvc.perform(
                        post("/api/auth/signup")
                                .contentType("application/json")
                                .param("sendWelcomeMail", "true")
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    public void UsernameExistSignUpReturnsStatusConflict() throws Exception {
        User user = createTestUser();
        SignupRequest req = new SignupRequest();
        req.setFullName(user.getFullName());
        req.setUsername(user.getUsername());
        req.setEmail("qwe@gmail.com");
        req.setPassword("password");

        mvc.perform(
                        post("/api/auth/signup")
                                .contentType("application/json")
                                .param("sendWelcomeMail", "true")
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isConflict());
    }

    @Test
    public void EmailExistSignUpReturnsStatusConflict() throws Exception {
        User user = createTestUser();
        SignupRequest req = new SignupRequest();
        req.setFullName(user.getFullName());
        req.setUsername("newUsername");
        req.setEmail(user.getEmail());
        req.setPassword("password");

        mvc.perform(
                        post("/api/auth/signup")
                                .contentType("application/json")
                                .param("sendWelcomeMail", "true")
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isConflict());
    }

    @AfterEach
    public void resetDb(){
        userRepository.deleteAll();
    }
}
