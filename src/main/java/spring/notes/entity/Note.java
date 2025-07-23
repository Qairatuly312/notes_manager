package spring.notes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "note", schema = "notes_man")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "note_header", length = Integer.MAX_VALUE)
    private String noteHeader;

    @Column(name = "note_body", length = Integer.MAX_VALUE)
    private String noteBody;

}