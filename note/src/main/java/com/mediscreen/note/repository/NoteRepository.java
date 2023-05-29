package com.mediscreen.note.repository;

import com.mediscreen.note.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findByPatientId(Integer patId);

    void deleteAllByPatientId(Integer patId);

}
