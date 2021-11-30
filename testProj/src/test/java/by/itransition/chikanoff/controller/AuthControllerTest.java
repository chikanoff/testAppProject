package by.itransition.chikanoff.controller;

import by.itransition.chikanoff.IntegrationTestBase;
import by.itransition.chikanoff.beans.User;
import by.itransition.chikanoff.payloads.request.LoginRequest;
import by.itransition.chikanoff.payloads.request.SignupRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends IntegrationTestBase {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void signInReturnsStatusOk() throws Exception {
        User user = createTestUser();
        LoginRequest req = new LoginRequest();
        req.setUsername(user.getUsername());
        req.setPassword("password");

        MvcResult result = mvc.perform(
                post("/api/auth/signin")
                   .contentType("application/json")
                   .param("sendWelcomeMail", "true")
                   .content(objectMapper.writeValueAsString(req)))
                   .andExpect(status().isOk()).andReturn();

        assertThat(200).isEqualTo(result.getResponse().getStatus());
    }

    @Test
    public void signInReturnsStatusUnauthorized() throws Exception {
        User user = createTestUser();
        LoginRequest req = new LoginRequest();
        req.setUsername("badUsername");
        req.setPassword("password");

        MvcResult result = mvc.perform(
                        post("/api/auth/signin")
                                .contentType("application/json")
                                .param("sendWelcomeMail", "true")
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized()).andReturn();

        assertThat(401).isEqualTo(result.getResponse().getStatus());
    }

    @Test
    public void signUpReturnsStatusOk() throws Exception {
        SignupRequest req = new SignupRequest();
        req.setFullName("testFullName");
        req.setUsername("testUsername");
        req.setEmail("email@gmail.com");
        req.setPassword("password");

        MvcResult result = mvc.perform(
                        post("/api/auth/signup")
                                .contentType("application/json")
                                .param("sendWelcomeMail", "true")
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk()).andReturn();

        assertThat(200).isEqualTo(result.getResponse().getStatus());
    }

    @Test
    public void usernameExistSignUpReturnsStatusConflict() throws Exception {
        User user = createTestUser();
        SignupRequest req = new SignupRequest();
        req.setFullName(user.getFullName());
        req.setUsername(user.getUsername());
        req.setEmail("qwe@gmail.com");
        req.setPassword("password");

        MvcResult result = mvc.perform(
                        post("/api/auth/signup")
                                .contentType("application/json")
                                .param("sendWelcomeMail", "true")
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isConflict()).andReturn();

        assertThat(409).isEqualTo(result.getResponse().getStatus());
    }

    @Test
    public void emailExistSignUpReturnsStatusConflict() throws Exception {
        User user = createTestUser();
        SignupRequest req = new SignupRequest();
        req.setFullName(user.getFullName());
        req.setUsername("newUsername");
        req.setEmail(user.getEmail());
        req.setPassword("password");

        MvcResult result = mvc.perform(
                        post("/api/auth/signup")
                                .contentType("application/json")
                                .param("sendWelcomeMail", "true")
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isConflict()).andReturn();
        assertThat(409).isEqualTo(result.getResponse().getStatus());
    }
}
