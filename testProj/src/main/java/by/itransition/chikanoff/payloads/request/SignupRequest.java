package by.itransition.chikanoff.payloads.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 128)
    private @Getter @Setter
    String fullName;

    @NotBlank
    @Size(min = 5, max = 32)
    private @Getter @Setter
    String username;

    @NotBlank
    @Size(max = 64)
    @Email
    private @Getter @Setter
    String email;


    @NotBlank
    @Size(min = 8, max = 40)
    private @Getter @Setter
    String password;

    @Override
    public String toString() {
        return "SignupRequest{" +
                "fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
