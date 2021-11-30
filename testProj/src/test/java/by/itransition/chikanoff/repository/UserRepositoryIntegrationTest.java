package by.itransition.chikanoff.repository;

import by.itransition.chikanoff.IntegrationTestBase;
import by.itransition.chikanoff.beans.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryIntegrationTest extends IntegrationTestBase {

    @Test
    public void findByNameReturnsUser() {
        // given
        User user = createTestUser();
        // when
        User found = getUserRepository().findByUsername(user.getUsername()).get();
        // then
        assertThat(found.getFullName()).isEqualTo(user.getFullName());
        assertThat(found.getEmail()).isEqualTo(user.getEmail());
        assertThat(found.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void findByEmailReturnsUser() {
        User user = createTestUser();
        assertThat(getUserRepository().existsByEmail(user.getEmail())).isTrue();
    }

    @Test
    public void existByUsernameReturnsTrue() {
        User user = createTestUser();

        assertThat(getUserRepository().existsByUsername(user.getUsername())).isTrue();
    }
}
