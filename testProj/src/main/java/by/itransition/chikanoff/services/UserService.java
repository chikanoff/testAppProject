package by.itransition.chikanoff.services;

import by.itransition.chikanoff.beans.User;
import by.itransition.chikanoff.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(User user){
        userRepository.saveAndFlush(user);
    }

    public boolean isEmailExist(String email){
        return userRepository.existsByEmail(email);
    }

    public boolean isUsernameExist(String username){
        return userRepository.existsByUsername(username);
    }
}
