package com.mediscreen.note.service;

import com.mediscreen.note.model.Note;
import com.mediscreen.note.repository.NoteRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
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

    public Optional<Note> getPatientById(String noteId) {

        logger.debug("Service: getPatientById - called");
        Optional<Note> note = noteRepository.findById(noteId);
        if (note.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Id patient : " + noteId + " does not exist");
        return note;
    }

}
