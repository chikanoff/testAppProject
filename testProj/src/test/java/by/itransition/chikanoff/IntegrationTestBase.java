package by.itransition.chikanoff;

import by.itransition.chikanoff.beans.User;
import by.itransition.chikanoff.repository.UserRepository;
import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@Getter
public abstract class IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public User createTestUser() {
        return userRepository.saveAndFlush(new User(
                "testFullName",
                "test",
                "testEmail@gmail.com",
                encoder.encode("password")
        ));
    }

    @AfterEach
    public void resetDb() {
        userRepository.deleteAll();
    }
}
