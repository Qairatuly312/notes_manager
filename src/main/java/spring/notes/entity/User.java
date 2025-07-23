package spring.notes.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "user", schema = "notes_man")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @ToString.Exclude
    private Long id;

    @Column(name = "email", nullable = false, length = Integer.MAX_VALUE, unique = true)
    private String email;

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE, unique = true)
    private String name;

    @Column(name = "password", nullable = false, length = Integer.MAX_VALUE)
    @ToString.Exclude
    private String password;
}