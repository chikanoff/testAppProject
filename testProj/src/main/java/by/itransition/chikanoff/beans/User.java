package by.itransition.chikanoff.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter Long id;

    @Column(name = "fullName", nullable = false, length = 128)
    private @Getter @Setter
    String fullName;

    @Column(nullable = false, unique = true, length = 128)
    private @Getter @Setter
    String username;

    @Column(nullable = false, unique = true, length = 128)
    private @Getter @Setter
    String email;

    @Column(nullable = false, length = 256)
    private @Getter @Setter
    String password;

    public User(String fullName, String username, String email, String password) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
