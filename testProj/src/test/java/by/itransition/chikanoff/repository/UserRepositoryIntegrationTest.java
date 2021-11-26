package by.itransition.chikanoff.repository;

import by.itransition.chikanoff.beans.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByName_thenReturnUser() {
        // given
        User user = new User("asd", "user", "email@gma.co", "password");
        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findByUsername(user.getUsername()).get();

        // then
        assertThat(found).isEqualTo(user);
    }

    @Test
    public void whenEmailExist_thenReturnTrue(){
        User user = new User("asd", "user", "email@gma.co", "password");
        entityManager.persist(user);
        entityManager.flush();

        assertThat(userRepository.existsByEmail("email@gma.co")).isTrue();
    }

    @Test
    public void whenUsernameExist_thenReturnTrue(){
        User user = new User("asd", "user", "email@gma.co", "password");
        entityManager.persist(user);
        entityManager.flush();

        assertThat(userRepository.existsByUsername("user")).isTrue();
    }
}
