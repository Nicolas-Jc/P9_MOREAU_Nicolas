package com.mediscreen.note.service;

import com.mediscreen.note.model.Note;
import com.mediscreen.note.repository.NoteRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private static final Logger logger = LogManager.getLogger(NoteService.class);

    private final NoteRepository noteRepository;


    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotes() {

        logger.debug("Service: getAllNotes - called");
        List<Note> notes = noteRepository.findAll();
        logger.debug("Service: getAllNotes - succes");
        return notes;
    }

    public Optional<Note> getNoteById(String noteId) {

        logger.debug("Service: getNoteById - called");
        Optional<Note> note = noteRepository.findById(noteId);
        if (note.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Id note : " + noteId + " does not exist");
        return note;
    }

    public Note addNote(Note noteToAdd) {
        logger.debug("Service : addNote - called");
        /*LocalDate currentDate = LocalDate.now(Clock.systemUTC());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(dateTimeFormatter);
        noteToAdd.setNoteDate(formattedDate);*/
        noteToAdd.setId(null);
        Note noteToSave = noteRepository.insert(noteToAdd);
        logger.debug("Service : addNote - succes");
        return noteToSave;
    }

    public Note updateNote(Note note) {

        logger.debug("Service : update Note - supply");
        if (note.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is empty !");

        Note noteToUpdate = noteRepository.findById(note.getId()).orElse(null);
        if (noteToUpdate == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note Id : " + note.getId() + " cannot be found");

        }

        logger.info("Service : update Note - check");
        return noteRepository.save(note);
    }

    public void deleteNoteById(String noteId) {

        logger.debug("Service : delete Note - supply");

        if (!noteRepository.existsById(noteId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deletion impossible, note id : "
                    + noteId + " cannot be found");
        noteRepository.deleteById(noteId);
        logger.info("Service : delete Note - check");
    }

    public List<Note> getNotesByPatientId(int patientId) {

        logger.debug("Service: getNotesByPatientId - called");
        return noteRepository.findByPatientId(patientId);
    }

    public void deleteAllNotesByPatientId(Integer patientId) {
        logger.debug("Service : delete All Notes - supply");

        noteRepository.deleteAllByPatientId(patientId);
        logger.info("Service : delete All Notes - check");
    }
}
