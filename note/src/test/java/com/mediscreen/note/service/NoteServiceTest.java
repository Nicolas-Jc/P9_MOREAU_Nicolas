package com.mediscreen.note.service;

import com.mediscreen.note.model.Note;
import com.mediscreen.note.repository.NoteRepository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@DisplayName("Note Service Tests")
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    Note note1;
    Note note2;
    Note note3;

    List<Note> listNote;

    @Test
    void getAllNotesTest() {
        // GIVEN
        note1 = new Note(1, "2000-01-01", "Comment Note1");
        note2 = new Note(2, "2000-02-02", "Comment Note2");
        note3 = new Note(3, "2000-03-03", "Comment Note3");

        listNote = new ArrayList<>();
        listNote.add(note1);
        listNote.add(note2);
        listNote.add(note3);

        when(noteRepository.findAll()).thenReturn(listNote);
        // WHEN
        List<Note> results = noteService.getAllNotes();
        // THEN
        assertEquals("Comment Note2", results.get(1).getDoctorNote());
        assertEquals(3, results.size());
    }

    @Test
    void getNoteByIdTest() {
        // GIVEN
        Note note = new Note(10, "2010-10-10", "Comment Note Patient 10");
        when(noteRepository.findById("33")).thenReturn(Optional.of(note));
        // WHEN
        Optional<Note> results = noteService.getNoteById("33");
        // THEN
        assertEquals(note, results.get());
    }

    @Test
    public void getNoteByIDButNotExistTest() {

        assertThrows(ResponseStatusException.class, () -> noteService.getNoteById("10"));
    }

    @Test
    void getNotesByPatientIdTest() {
        // GIVEN
        note1 = new Note(44, "2000-01-01", "Comment Note1");
        note2 = new Note(44, "2000-02-02", "Comment Note2");
        note3 = new Note(44, "2000-02-03", "Comment Note3");
        listNote = new ArrayList<>();
        listNote.add(note1);
        listNote.add(note2);
        listNote.add(note3);

        when(noteRepository.findByPatientId(44)).thenReturn(listNote);
        // WHEN
        List<Note> results = noteService.getNotesByPatientId(44);
        // THEN
        assertEquals(listNote, results);
    }

    @Test
    void addNoteTest() {
        //GIVEN
        Note note5 = new Note("XXXXXXXXXXXXXXXXXXXXXXX", 11, "2010-10-10", "Comment Note Patient 11");
        //WHEN
        //when(noteRepository.i(note5)).thenReturn(note5);
        when(noteRepository.insert(any(Note.class))).thenReturn(note5);
        Note response = noteService.addNote(note5);
        // THEN
        assertEquals(note5, response);
    }

    @Test
    void updateNoteTest() {
        //GIVEN
        note1 = new Note(5, "2005-05-05", "Comment Note Patient 5");
        note1.setId("XXX");
        when(noteRepository.save(note1)).thenReturn(note1);
        when(noteRepository.findById("XXX")).thenReturn(Optional.of(note1));
        // WHEN
        Note response = noteService.updateNote(note1);
        // THEN
        assertEquals(note1, response);
    }

    @Test
    public void updateNoteButIdIsNullTest() {
        assertThrows(ResponseStatusException.class, ()
                -> noteService.updateNote(
                new Note("XXXXXXXXXXXXXXXXXXXXXX", 11, "2010-10-10", "Comment Note Patient 11")));
    }

    @Test
    void deleteNoteByIdTest() {
        //GIVEN
        note1 = new Note(1, "2000-01-01", "Comment Note1");
        note1.setId("ZZZZZZZZZZ");
        when(noteRepository.existsById("ZZZZZZZZZZ")).thenReturn(Boolean.TRUE);
        // WHEN
        noteService.deleteNoteById("ZZZZZZZZZZ");
        // THEN
        verify(noteRepository, times(1)).deleteById("ZZZZZZZZZZ");

    }

    @Test
    public void deleteNoteButIdNotExistTest() {
        assertThrows(ResponseStatusException.class, () -> noteService.deleteNoteById("PPPPPPPPPPPPP"));
    }

    @Test
    void deleteAllNoteByPatientIdTest() {
        //GIVEN
        note1 = new Note(1, "2000-01-01", "Comment Note1");
        note2 = new Note(2, "2000-02-02", "Comment Note2");
        note3 = new Note(2, "2000-03-03", "Comment Note3");
        Note note4 = new Note(3, "2000-03-03", "Comment Note4");
        Note note5 = new Note(3, "2000-03-03", "Comment Note5");
        Note note6 = new Note(3, "2000-03-03", "Comment Note6");
        // WHEN
        noteService.deleteAllNotesByPatientId(2);
        // THEN
        verify(noteRepository, times(1)).deleteAllByPatientId(2);

    }
}
