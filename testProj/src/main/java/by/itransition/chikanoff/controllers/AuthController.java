package by.itransition.chikanoff.controllers;

import by.itransition.chikanoff.beans.User;
import by.itransition.chikanoff.jwt.JwtUtils;
import by.itransition.chikanoff.payloads.request.LoginRequest;
import by.itransition.chikanoff.payloads.request.SignupRequest;
import by.itransition.chikanoff.payloads.response.JwtResponse;
import by.itransition.chikanoff.payloads.response.MessageResponse;
import by.itransition.chikanoff.repository.UserRepository;
import by.itransition.chikanoff.services.AuthService;
import by.itransition.chikanoff.services.UserDetailsImpl;
import by.itransition.chikanoff.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    private final PasswordEncoder encoder;

    @PostMapping("/signin")
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userService.isUsernameExist(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new MessageResponse("Error: Username is already taken!"), HttpStatus.CONFLICT);
        }

        if (userService.isEmailExist(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new MessageResponse("Error: Email is already in use!"), HttpStatus.CONFLICT);
        }

        userService.createUser(new User( signUpRequest.getFullName(),
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())));

        return new ResponseEntity<>(new MessageResponse("User registered successfully!"), HttpStatus.OK);
    }
}
