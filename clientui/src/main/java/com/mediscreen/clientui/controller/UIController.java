package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.beans.AssessmentBean;
import com.mediscreen.clientui.beans.NoteBean;
import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.proxies.AssessmentProxy;
import com.mediscreen.clientui.proxies.NotesProxy;
import com.mediscreen.clientui.proxies.PatientsProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import java.util.List;

@Controller
public class UIController {

    @Autowired
    private PatientsProxy patientsProxy;

    @Autowired
    private NotesProxy notesProxy;

    @Autowired
    private AssessmentProxy assessmentProxy;

    private static final Logger logger = LogManager.getLogger(UIController.class);


    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    // view Patients List
    @GetMapping("/patients")
    public String showPatientsList(Model model) {
        //get all patients:
        List<PatientBean> patients = patientsProxy.getAllPatients();
        model.addAttribute("patients", patients);
        //get note count per patient:
        //Map<Integer, Integer> mapCountOfNotesPerPatient = notesProxy.getCountOfNotesPerPatient();
        //model.addAttribute("mapcountnote", mapCountOfNotesPerPatient);
        return "patients";
    }

    // view Patient/add
    @GetMapping("/patient/add")
    public String addPatientForm(Model model) {
        model.addAttribute("patient", new PatientBean());
        logger.info("View Patient Add loaded");
        return "patientAdd";
    }

    // Button Add Patient To List
    @PostMapping("/patient/validate")
    public String validate(@Valid @ModelAttribute("patient") PatientBean patientBean,
                           BindingResult result, RedirectAttributes redirAttrs) {

        if (result.hasErrors()) {
            return "patientAdd";
        }

        // Cas Patient dejà en BDD
        if (patientsProxy.checkExistPatient(patientBean).equals(Boolean.TRUE)) {
            /*redirAttrs.addFlashAttribute("errorCreateMessage",
                    "This patient already exists in the database");*/
            result.rejectValue("lastName", "", "This patient already exists in the database");
            result.rejectValue("firstName", "", "This patient already exists in the database");
            result.rejectValue("birthDate", "", "This patient already exists in the database");
            return "patientAdd";
        }

        if (patientBean.getId() == null) {
            patientsProxy.addPatient(patientBean);
            redirAttrs.addFlashAttribute("successSaveMessage",
                    "Patient successfully added to list");
            return "redirect:/patients";
        }

      /*  if (!result.hasErrors()) {
            patientsProxy.addPatient(patientBean);
            redirAttrs.addFlashAttribute("successSaveMessage",
                    "Patient successfully added to list");
            return "redirect:/patients";
        }*/
        /*if (patientsProxy.checkExistPatient(patientBean).equals(Boolean.TRUE)) {
            redirAttrs.addFlashAttribute("errorDeleteMessage",
                    "This patient already exists in the database");
            logger.error("Error creation Patient");
        }*/
        return "patientAdd";
    }

    @GetMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable("id") Integer id, Model model, RedirectAttributes redirAttrs) {
        try {
           /* if (!patientsProxy.getPatientById(id)) {
                logger.error("Patient {} cannot be found'", id);
                return "redirect:/patients";
            }*/
            patientsProxy.deletePatient(id);
            notesProxy.deleteAllPatientNotes(id);
            model.addAttribute("patients", patientsProxy.getAllPatients());
            redirAttrs.addFlashAttribute("successDeleteMessage", "Patient successfully deleted");
        } catch (Exception e) {
            redirAttrs.addFlashAttribute("errorDeleteMessage", "Error during deletion");
            logger.error("Error to delete \"Patient\" : {}", id);
        }
        return "redirect:/patients";
    }

    @GetMapping("/patients/{id}")
    public String patientNotes(@PathVariable Integer id, Model model) {

        PatientBean patient = patientsProxy.getPatientById(id);
        model.addAttribute("patient", patient);
        // Charge la Liste des Notes du Patient
        List<NoteBean> listNotes = notesProxy.getNotesByPatient(id);
        model.addAttribute("listNotes", listNotes);

        return "patientNotesAss";
    }

   /* @GetMapping("/noteAdd")
    public String showNoteForm(@RequestParam(required = false) String noteId, @RequestParam Integer patientId, Model model) {

        //id is unset: create note
        if (noteId == null) {
            NoteBean newNoteBean = new NoteBean();
            newNoteBean.setPatientId(patientId);
            model.addAttribute("note", newNoteBean);
        }
        //id is set: update note
        else {
            model.addAttribute("note", notesProxy.getNote(noteId));
        }
        return "noteAdd";
    }*/

    // view Note/add
    @GetMapping("/noteAdd")
    public String addNoteForm(Model model) {
        model.addAttribute("note", new NoteBean());
        logger.info("View Note Add loaded");
        return "noteAdd";
    }

    @PostMapping("/noteAdd")
    public String validate(@Valid @ModelAttribute("note") NoteBean noteBean,
                           BindingResult result, RedirectAttributes redirAttrs) {

        if (result.hasErrors()) {
            return "noteAdd";
        }

      /*  // Cas Patient dejà en BDD
        if (patientsProxy.checkExistPatient(noteBean).equals(Boolean.TRUE)) {
            *//*redirAttrs.addFlashAttribute("errorCreateMessage",
                    "This patient already exists in the database");*//*
            result.rejectValue("lastName", "", "This patient already exists in the database");
            result.rejectValue("firstName", "", "This patient already exists in the database");
            result.rejectValue("birthDate", "", "This patient already exists in the database");
            return "patientAdd";
        }*/

        if (noteBean.getId() == null) {
            notesProxy.addNote(noteBean);
            redirAttrs.addFlashAttribute("successSaveMessage",
                    "Note successfully added to list");
            return "redirect:/patientNotesAss";
        }

        return "noteAdd";
    }

   /* @GetMapping(value = "/patients/{id}/diabeteAssess",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String patientAssessment(@PathVariable Integer id) {

        return assessmentProxy.getRiskLevelByPatient(id);
    }*/

    // **************** A REVOIR ******************************************
    @GetMapping("/assessment/{id}")
    public String showDiabeteAssessment(@PathVariable Integer id, Model model) {
        //get all patients:
        String assessment = assessmentProxy.getRiskLevelByPatient(id);
        model.addAttribute("assessment", assessment);
        //get note count per patient:
        //Map<Integer, Integer> mapCountOfNotesPerPatient = notesProxy.getCountOfNotesPerPatient();
        //model.addAttribute("mapcountnote", mapCountOfNotesPerPatient);
        return "patients";
    }


}
