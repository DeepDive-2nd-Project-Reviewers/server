package reviewers.server.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity(name = "user")
@Table(name = "user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String email;
    private String password;
    private String username;
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String email, String encodedPassword, String username, LocalDate birth, String role) {
        this.email = email;
        this.password = encodedPassword;
        this.username = username;
        this.birth = birth;
        this.role = role;
    }

    public User update(String username) {
        this.username = username;
        return this;
    }
}
