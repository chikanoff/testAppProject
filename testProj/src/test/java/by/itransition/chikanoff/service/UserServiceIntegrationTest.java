package by.itransition.chikanoff.service;

import by.itransition.chikanoff.beans.User;
import by.itransition.chikanoff.payloads.request.SignupRequest;
import by.itransition.chikanoff.repository.UserRepository;
import by.itransition.chikanoff.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class UserServiceIntegrationTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserService userService;

    @Test
    public void createUserTest(){
        SignupRequest req = new SignupRequest();
        req.setFullName("user1");
        req.setUsername("user1");
        req.setEmail("user1@gmail.com");
        req.setPassword("password");

        userService.createUser(req);

        List<User> users = userRepository.findAll();
        User user = users.get(users.size()-1);
        assertThat(user).isEqualTo(new User(req.getFullName(), req.getUsername(), req.getEmail(), req.getPassword()));
    }
}
