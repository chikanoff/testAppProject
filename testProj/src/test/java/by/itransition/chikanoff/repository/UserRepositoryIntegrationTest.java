package by.itransition.chikanoff.repository;

import by.itransition.chikanoff.IntegrationTestBase;
import by.itransition.chikanoff.beans.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class UserRepositoryIntegrationTest extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByName_thenReturnUser() {
        // given
        User user = createTestUser();

        // when
        User found = userRepository.findByUsername(user.getUsername()).get();

        // then
        assertThat(found).isEqualTo(user);
    }

    @Test
    public void whenEmailExist_thenReturnTrue(){
        User user = createTestUser();

        assertThat(userRepository.existsByEmail(user.getEmail())).isTrue();
    }

    @Test
    public void whenUsernameExist_thenReturnTrue(){
        User user = createTestUser();

        assertThat(userRepository.existsByUsername(user.getUsername())).isTrue();
    }
}
