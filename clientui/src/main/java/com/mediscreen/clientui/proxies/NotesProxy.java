package com.mediscreen.clientui.proxies;

import com.mediscreen.clientui.beans.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-notes", url = "${microservice-notes.url}")
public interface NotesProxy {

    @GetMapping("/notes")
    List<NoteBean> getAllNotes();

    @GetMapping("/notes/{id}")
    NoteBean getNote(@PathVariable String id);

    @PostMapping("/notes/add")
    NoteBean addNote(@RequestBody final NoteBean noteToAdd);

    @PutMapping("/notes")
    NoteBean updatePatient(@RequestBody NoteBean patientToUpdate);

    @DeleteMapping("/notes/delete/{id}")
    void deleteNote(@PathVariable("id") final String noteId);

    @GetMapping("/patients/{patientId}/notes")
    List<NoteBean> getNotesByPatient(@PathVariable Integer patientId);

    @DeleteMapping(value = "/patients/{patientId}/notes/delete")
    void deleteAllPatientNotes(@PathVariable Integer patientId);
}
