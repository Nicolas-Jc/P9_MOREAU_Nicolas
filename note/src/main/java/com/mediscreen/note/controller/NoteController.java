package com.mediscreen.note.controller;

import com.mediscreen.note.model.Note;
import com.mediscreen.note.service.NoteService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/notes")
    public List<Note> getNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/notes/{id}")
    public Optional<Note> getNote(@PathVariable String id) {
        return noteService.getPatientById(id);
    }
}
