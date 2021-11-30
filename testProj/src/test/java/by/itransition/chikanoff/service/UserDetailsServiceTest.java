package by.itransition.chikanoff.service;

import by.itransition.chikanoff.IntegrationTestBase;
import by.itransition.chikanoff.beans.User;
import by.itransition.chikanoff.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserDetailsServiceTest extends IntegrationTestBase {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void loadByUsernameReturnsCorrectUsername(){
        User user = createTestUser();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        assertThat(userDetails.getUsername()).isEqualTo(user.getUsername());
    }
}
