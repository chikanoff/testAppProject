package by.itransition.chikanoff;

import by.itransition.chikanoff.beans.User;
import by.itransition.chikanoff.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public User createTestUser(){
        return userRepository.saveAndFlush(new User(
                "testFullName",
                "testUsername",
                "testEmail@gmail.com",
                encoder.encode("password")
        ));
    }
}
