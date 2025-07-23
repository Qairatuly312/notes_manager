package spring.notes.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.notes.entity.Note;
import spring.notes.entity.User;
import spring.notes.repositories.NoteRepository;
import spring.notes.repositories.UserRepository;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public Note createNote(String username, Note note) {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        note.setUser(user);
        return noteRepository.save(note);
    }

    public List<Note> getNotes(String username) {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return noteRepository.findByUserId(user.getId());
    }

}
