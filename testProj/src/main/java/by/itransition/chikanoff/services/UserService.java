package by.itransition.chikanoff.services;

import by.itransition.chikanoff.beans.User;
import by.itransition.chikanoff.exceptions.DataExistException;
import by.itransition.chikanoff.payloads.request.SignupRequest;
import by.itransition.chikanoff.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(SignupRequest signupRequest) {
        checkUsernameExist(signupRequest.getUsername());
        checkEmailExist(signupRequest.getEmail());

        userRepository.saveAndFlush(new User(signupRequest.getFullName(),
                                             signupRequest.getUsername(),
                                             signupRequest.getEmail(),
                                             signupRequest.getPassword()));
    }

    private void checkEmailExist(String email) throws DataExistException {
        if(userRepository.existsByEmail(email)){
            throw new DataExistException("User with this email already exist");
        }
    }

    private void checkUsernameExist(String username) throws DataExistException {
        if(userRepository.existsByUsername(username)){
            throw new DataExistException("User with this username already exist");
        }
    }
}
