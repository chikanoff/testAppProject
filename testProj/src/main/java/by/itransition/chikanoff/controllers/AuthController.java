package by.itransition.chikanoff.controllers;

import by.itransition.chikanoff.exceptions.DataExistException;
import by.itransition.chikanoff.payloads.request.LoginRequest;
import by.itransition.chikanoff.payloads.request.SignupRequest;
import by.itransition.chikanoff.payloads.response.JwtResponse;
import by.itransition.chikanoff.payloads.response.MessageResponse;
import by.itransition.chikanoff.services.AuthService;
import by.itransition.chikanoff.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        userService.createUser(signUpRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
