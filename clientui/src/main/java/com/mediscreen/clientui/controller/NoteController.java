package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.model.NoteModel;
import com.mediscreen.clientui.proxies.NotesProxy;
import com.mediscreen.clientui.proxies.PatientsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    // view Note add-update
    @GetMapping("/noteInput")
    public String showNoteForm(@RequestParam(required = false) String id,
                               @RequestParam Integer patientId, Model model) {

        if (id == null) {
            NoteModel newNoteBean = new NoteModel();
            newNoteBean.setPatientId(patientId);
            model.addAttribute("note", newNoteBean);
        } else {
            model.addAttribute("note", notesProxy.getNote(id));
        }
        return "noteInput";
    }


    @PostMapping("/noteInput")
    public String validate(@Valid @ModelAttribute("note") NoteModel noteBean,
                           BindingResult result, RedirectAttributes redirAttrs) {

        if (result.hasErrors()) {
            return "noteInput";
        }

        if (noteBean.getId() == null || noteBean.getId().equals("")) {

            LocalDate currentDate = LocalDate.now(Clock.systemUTC());
            String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            noteBean.setNoteDate(formattedDate);
            notesProxy.addNote(noteBean);
            redirAttrs.addFlashAttribute("successSaveMessage",
                    "Note successfully added to list");
            return "redirect:/patients/" + noteBean.getPatientId();
        } else {
            notesProxy.updateNote(noteBean);
            redirAttrs.addFlashAttribute("successSaveMessage",
                    "Note successfully updated");
            return "redirect:/patients/" + noteBean.getPatientId();
        }

    }

    @PostMapping("/patients/{patientId}/notes/delete/{id}")
    public String deleteNote(@PathVariable Integer patientId, @PathVariable String id) {

        notesProxy.deleteNote(id);
        return "redirect:/patients/" + patientId;
    }

}
