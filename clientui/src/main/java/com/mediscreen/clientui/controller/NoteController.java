package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.beans.NoteBean;
import com.mediscreen.clientui.proxies.NotesProxy;
import com.mediscreen.clientui.proxies.PatientsProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class NoteController {

    @Autowired
    private NotesProxy notesProxy;

    @Autowired
    private PatientsProxy patientsProxy;

    private static final Logger logger = LogManager.getLogger(NoteController.class);

    // view Note add-update
    @GetMapping("/noteAdd")
    public String showNoteForm(@RequestParam(required = false) String id,
                               @RequestParam Integer patientId, Model model) {

        if (id == null) {
            NoteBean newNoteBean = new NoteBean();
            newNoteBean.setPatientId(patientId);
            model.addAttribute("note", newNoteBean);
        } else {
            model.addAttribute("note", notesProxy.getNote(id));
        }
        return "noteAdd";
    }


    @PostMapping("/noteAdd")
    public String validate(@Valid @ModelAttribute("note") NoteBean noteBean,
                           BindingResult result, RedirectAttributes redirAttrs) {

        if (result.hasErrors()) {
            return "noteAdd";
        }

        if (noteBean.getId() == null || noteBean.getId().equals("")) {
            //noteBean.setId("6178f9e3c2533871717abaf2");
            //noteBean.setNoteDate("2000-03-03");
            LocalDate currentDate = LocalDate.now(Clock.systemUTC());
            //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            noteBean.setNoteDate(formattedDate);
            notesProxy.addNote(noteBean);
            redirAttrs.addFlashAttribute("successSaveMessage",
                    "Note successfully added to list");
            //return "redirect:/patientNotesAss";
            return "redirect:/patients/" + noteBean.getPatientId();
        } else {
            notesProxy.updateNote(noteBean);
            redirAttrs.addFlashAttribute("successSaveMessage",
                    "Note successfully updated");
            return "redirect:/patients/" + noteBean.getPatientId();
        }
        //return "noteAdd";
        //return "redirect:/patients/" + noteBean.getPatientId();

    }

   /* @GetMapping(value = "/patients/{id}/diabeteAssess",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String patientAssessment(@PathVariable Integer id) {

        return assessmentProxy.getRiskLevelByPatient(id);
    }*/

    // **************** A REVOIR ******************************************
    /*@GetMapping("/assessment/{id}")
    public String showDiabeteAssessment(@PathVariable Integer id, Model model) {
        //get all patients:
        String assessment = assessmentProxy.getRiskLevelByPatient(id);
        model.addAttribute("assessment", assessment);
        //get note count per patient:
        //Map<Integer, Integer> mapCountOfNotesPerPatient = notesProxy.getCountOfNotesPerPatient();
        //model.addAttribute("mapcountnote", mapCountOfNotesPerPatient);
        return "patients";
    }*/

    /*@DeleteMapping("/notes/delete/{id}")
    public String deleteNote(@PathVariable("id") Integer id, Model model, RedirectAttributes redirAttrs) {
        try {
            patientsProxy.deletePatient(id);
            notesProxy.deleteAllPatientNotes(id);
            model.addAttribute("patients", patientsProxy.getAllPatients());
            redirAttrs.addFlashAttribute("successDeleteMessage", "Patient successfully deleted");
        } catch (Exception e) {
            redirAttrs.addFlashAttribute("errorDeleteMessage", "Error during deletion");
            logger.error("Error to delete \"Patient\" : {}", id);
        }
        return "redirect:/patients";
    }*/

    @PostMapping("/patients/{patientId}/notes/delete/{id}")
    public String deleteNote(@PathVariable Integer patientId, @PathVariable String id) {

        notesProxy.deleteNote(id);
        return "redirect:/patients/" + patientId;
    }

}
