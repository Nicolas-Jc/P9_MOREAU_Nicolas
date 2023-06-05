package com.mediscreen.assessment.proxies;

import com.mediscreen.assessment.beans.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-notes", url = "${microservice-notes.url}")
public interface NotesProxy {
    @GetMapping("/patients/{patientId}/notes")
    List<NoteBean> getNotesByPatient(@PathVariable Integer patientId);

}
