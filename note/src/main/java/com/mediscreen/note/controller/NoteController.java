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
        return noteService.getNoteById(id);
    }

    @PostMapping("/notes/add")
    public Note addNote(@Valid @RequestBody final Note noteToAdd) {
        return noteService.addNote(noteToAdd);
    }

    @PutMapping("/notes")
    public Note updateNote(@Valid @RequestBody Note noteToUpdate) {
        return noteService.updateNote(noteToUpdate);
    }

    @DeleteMapping("/notes/delete/{id}")
    public void deleteNote(@PathVariable("id") final String noteId) {
        noteService.deleteNoteById(noteId);
    }

    @GetMapping("/patients/{patientId}/notes")
    public List<Note> getNotesByPatient(@PathVariable Integer patientId) {
        return noteService.getNotesByPatientId(patientId);
    }

    @DeleteMapping(value = "/patients/{patientId}/notes/delete")
    public void deleteAllPatientNotes(@PathVariable Integer patientId) {
        noteService.deleteAllNotesByPatientId(patientId);
    }


}
