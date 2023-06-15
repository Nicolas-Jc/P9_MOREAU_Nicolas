package com.mediscreen.clientui.proxies;

import com.mediscreen.clientui.model.NoteModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-notes", url = "${microservice-notes.url}")
public interface NotesProxy {

    @GetMapping("/notes")
    List<NoteModel> getAllNotes();

    @GetMapping("/notes/{id}")
    NoteModel getNote(@PathVariable String id);

    @PostMapping("/notes/add")
    NoteModel addNote(@RequestBody final NoteModel noteToAdd);

    @PutMapping("/notes")
    NoteModel updateNote(@RequestBody NoteModel noteToUpdate);

    @DeleteMapping("/notes/delete/{id}")
    void deleteNote(@PathVariable("id") final String noteId);

    @GetMapping("/patients/{patientId}/notes")
    List<NoteModel> getNotesByPatient(@PathVariable Integer patientId);

    @DeleteMapping(value = "/patients/{patientId}/notes/delete")
    void deleteAllPatientNotes(@PathVariable Integer patientId);
}
