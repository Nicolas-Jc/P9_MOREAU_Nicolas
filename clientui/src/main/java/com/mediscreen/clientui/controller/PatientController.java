package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.beans.NoteBean;
import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.proxies.AssessmentProxy;
import com.mediscreen.clientui.proxies.NotesProxy;
import com.mediscreen.clientui.proxies.PatientsProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.List;

@Controller
public class PatientController {

    @Autowired
    private PatientsProxy patientsProxy;

    @Autowired
    private NotesProxy notesProxy;

    @Autowired
    private AssessmentProxy assessmentProxy;

    /*@Autowired
    private AssessmentProxy assessmentProxy;*/

    private static final Logger logger = LogManager.getLogger(PatientController.class);


    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    // view Patients List
    @GetMapping("/patients")
    public String showPatientsList(Model model) {
        List<PatientBean> patients = patientsProxy.getAllPatients();
        model.addAttribute("patients", patients);

        return "patients";
    }

    // view Patient/add
   /* @GetMapping("/patient/add")
    public String addPatientForm(Model model) {
        model.addAttribute("patient", new PatientBean());
        logger.info("View Patient Add loaded");
        return "patientAdd";
    }*/

    @GetMapping("/patientAdd")
    public String showPatientForm(@RequestParam(required = false) Integer id, Model model) {

        if (id == null) {
            model.addAttribute("patient", new PatientBean());
        } else {
            model.addAttribute("patient", patientsProxy.getPatientById(id));
        }
        return "patientAdd";
    }

    // Button Add / Update Patient To List
    @PostMapping("/patient/validate")
    public String validate(@Valid @ModelAttribute("patient") PatientBean patientBean,
                           BindingResult result, RedirectAttributes redirAttrs) {

        if (result.hasErrors()) {
            return "patientAdd";
        }

        // Cas creation Patient dejà en BDD
        if (patientBean.getId() == null && patientsProxy.checkExistPatient(patientBean).equals(Boolean.TRUE)) {
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
        } else {
            patientsProxy.updatePatient(patientBean);
            redirAttrs.addFlashAttribute("successSaveMessage",
                    "Patient successfully updated");
            return "redirect:/patients/" + patientBean.getId();
        }

    }

    @GetMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable("id") Integer id, Model model, RedirectAttributes redirAttrs) {
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
    }

    @GetMapping("/patients/{id}")
    public String patientNotes(@PathVariable Integer id, Model model) {
        // Charge les infos du patient
        PatientBean patient = patientsProxy.getPatientById(id);
        model.addAttribute("patient", patient);
        // Charge la Liste des Notes du Patient
        List<NoteBean> listNotes = notesProxy.getNotesByPatient(id);
        model.addAttribute("listNotes", listNotes);
        // Charge le résultat Risque Diabete
        String diabetesResult = assessmentProxy.getRiskLevelByPatient(id);
        logger.info("diabetesResult : {}", diabetesResult);
        //model.addAttribute("diabeteResult", diabetesResult);

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
  /*  @GetMapping("/noteAdd")
    public String addNoteForm(Model model) {
        model.addAttribute("note", new NoteBean());
        logger.info("View Note Add loaded");
        return "noteAdd";

    }*/


}
