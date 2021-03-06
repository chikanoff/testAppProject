package by.itransition.chikanoff.service;

import by.itransition.chikanoff.beans.User;
import by.itransition.chikanoff.payloads.request.SignupRequest;
import by.itransition.chikanoff.repository.UserRepository;
import by.itransition.chikanoff.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceUnitTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void createUserReturnsEmailExist() {
        User user = new User("fullName", "username", "email@ia.co", "password");
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        SignupRequest req = new SignupRequest();
        req.setFullName(user.getFullName());
        req.setUsername(user.getUsername());
        req.setEmail(user.getEmail());
        req.setPassword(user.getPassword());

        assertThatThrownBy(() -> userService.createUser(req)).hasMessage("User with this email already exist");

    }

    @Test
    public void createUserReturnsUsernameExist() {
        User user = new User("fullName", "username", "email@ia.co", "password");
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        SignupRequest req = new SignupRequest();
        req.setFullName(user.getFullName());
        req.setUsername(user.getUsername());
        req.setEmail(user.getEmail());
        req.setPassword(user.getPassword());

        assertThatThrownBy(() -> userService.createUser(req)).hasMessage("User with this username already exist");

    }
}
