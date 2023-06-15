package com.mediscreen.assessment.proxies;

import com.mediscreen.assessment.model.NoteModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-notes", url = "${microservice-notes.url}")
public interface NotesProxy {
    @GetMapping("/patients/{patientId}/notes")
    List<NoteModel> getNotesByPatient(@PathVariable Integer patientId);

}
