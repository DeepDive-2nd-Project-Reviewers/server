package reviewers.server.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewers.server.domain.comment.entity.Comment;
import reviewers.server.domain.heart.content.entity.ContentHeart;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ContentHeart> contentHearts = new ArrayList<>();

    @Builder
    public User(String email, String encodedPassword, String username, LocalDate birth) {
        this.email = email;
        this.password = encodedPassword;
        this.username = username;
        this.birth = birth;
        this.role = Role.USER;
    }

    public User(String email, String encodedPassword){
        this.email = email;
        this.password = encodedPassword;
        this.role = Role.USER;
    }

    public User update(String username) {
        this.username = username;
        return this;
    }
}
