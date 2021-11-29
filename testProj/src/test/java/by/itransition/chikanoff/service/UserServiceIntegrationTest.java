package by.itransition.chikanoff.service;

import by.itransition.chikanoff.beans.User;
import by.itransition.chikanoff.payloads.request.SignupRequest;
import by.itransition.chikanoff.repository.UserRepository;
import by.itransition.chikanoff.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class UserServiceIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

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
        assertThat(user.getUsername()).isEqualTo(req.getUsername());
        assertThat(user.getEmail()).isEqualTo(req.getEmail());
    }

    @Test
    public void checkEmailExistTest(){
        User user = createTestUser();
        SignupRequest req = new SignupRequest();
        req.setFullName(user.getFullName());
        req.setUsername("username");
        req.setEmail(user.getEmail());
        req.setPassword("password");

        assertThatThrownBy(() -> userService.createUser(req)).hasMessage("User with this email already exist");
    }

    @Test
    public void checkUsernameExistTest(){
        User user = createTestUser();
        SignupRequest req = new SignupRequest();
        req.setFullName(user.getFullName());
        req.setUsername(user.getUsername());
        req.setEmail("email");
        req.setPassword("password");

        assertThatThrownBy(() -> userService.createUser(req)).hasMessage("User with this username already exist");
    }

    private User createTestUser(){
        return userRepository.saveAndFlush(new User(
                "testFullName",
                "testUsername",
                "testEmail@gmail.com",
                encoder.encode("password")
        ));
    }

    @AfterEach
    public void resetDb(){
        userRepository.deleteAll();
    }
}
