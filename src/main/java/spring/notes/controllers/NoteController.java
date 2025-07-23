package spring.notes.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import spring.notes.entity.Note;
import spring.notes.entity.User;
import spring.notes.repositories.NoteRepository;
import spring.notes.repositories.UserRepository;
import spring.notes.services.NoteService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {
    final UserRepository userRepository;
    final NoteRepository noteRepository;
    final NoteService noteService;

    public NoteController(UserRepository userRepository, NoteRepository noteRepository, NoteService noteService) {
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
        this.noteService = noteService;
    }

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getUserNotes(Principal principal) {
        return ResponseEntity.ok(noteService.getNotes(principal.getName()));
    }

    @PostMapping("/notes")
    public ResponseEntity<?> createNote(@RequestBody Note note, Principal principal) {
        String username = principal.getName();

        return ResponseEntity.ok(noteService.createNote(username, note));
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<?> getNote(@PathVariable Long id, Principal principal) {
        User user = userRepository.findByName(principal.getName());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Note note = noteRepository.findByIdAndUserId(id, user.getId()).orElse(null);

        if (note == null) {
            throw new UsernameNotFoundException("Note not found");
        }

        return ResponseEntity.ok(note);
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody Note newNote, Principal principal) throws BadRequestException {
        User user = userRepository.findByName(principal.getName());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if(newNote == null || newNote.getNoteHeader() == null) {
            throw new BadRequestException("Note header is null");
        }

        Note note = noteRepository.findByIdAndUserId(id, user.getId()).orElse(null);
        if (note == null) {
            return ResponseEntity.status(404).body("Note not found or not owned by user");
        }

        note.setNoteBody(newNote.getNoteBody());
        note.setNoteHeader(newNote.getNoteHeader());

        return ResponseEntity.ok(noteRepository.save(note));
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id, Principal principal) {
        User user = userRepository.findByName(principal.getName());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Note note = noteRepository.findByIdAndUserId(id, user.getId()).orElse(null);
        if (note == null) {
            return ResponseEntity.status(404).body("Note not found or not owned by user");
        }

        noteRepository.delete(note);
        return ResponseEntity.ok().build();
    }

}
