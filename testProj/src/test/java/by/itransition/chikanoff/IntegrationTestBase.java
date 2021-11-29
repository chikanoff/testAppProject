package by.itransition.chikanoff;

import by.itransition.chikanoff.beans.User;
import by.itransition.chikanoff.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
public abstract class IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public User createTestUser(){
        return userRepository.saveAndFlush(new User(
                "testFullName",
                "testUsername",
                "testEmail@gmail.com",
                encoder.encode("password")
        ));
    }

    @AfterEach
    public void resetDb(){
        getUserRepository().deleteAll();
    }
}
