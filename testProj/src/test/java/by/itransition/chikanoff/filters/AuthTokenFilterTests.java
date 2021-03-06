package by.itransition.chikanoff.filters;

import by.itransition.chikanoff.IntegrationTestBase;
import by.itransition.chikanoff.beans.User;
import by.itransition.chikanoff.jwt.JwtUtils;
import by.itransition.chikanoff.payloads.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthTokenFilterTests extends IntegrationTestBase {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void authFilterTokenReturnsStatusOk() throws Exception {
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

        String token = result.getResponse().getContentAsString()
                             .replace("{\"token\":\"", "")
                             .replace("\"}", "");

        mvc.perform(get("/api/test/")
                   .accept(MediaType.APPLICATION_JSON)
                   .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                   .andExpect(status().isOk());

    }
}
