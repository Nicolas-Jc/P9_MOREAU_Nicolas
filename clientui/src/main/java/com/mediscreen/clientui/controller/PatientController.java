package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.model.NoteModel;
import com.mediscreen.clientui.model.PatientModel;
import com.mediscreen.clientui.proxies.AssessmentProxy;
import com.mediscreen.clientui.proxies.NotesProxy;
import com.mediscreen.clientui.proxies.PatientsProxy;
import feign.FeignException;
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

    private static final String REJECT_MESSAGE = "This patient already exists in the database";
    private static final String REDIRECT_PATIENTS = "redirect:/patients";


    private static final Logger logger = LogManager.getLogger(PatientController.class);


    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    // view Patients List
    @GetMapping("/patients")
    public String showPatientsList(Model model) {
        List<PatientModel> patients = patientsProxy.getAllPatients();
        model.addAttribute("patients", patients);

        return "patients";
    }


    @GetMapping("/patientInput")
    public String showPatientForm(@RequestParam(required = false) Integer id, Model model) {

        if (id == null) {
            model.addAttribute("patient", new PatientModel());
        } else {
            model.addAttribute("patient", patientsProxy.getPatientById(id));
        }
        return "patientInput";
    }

    // Button Add / Update Patient To List
    @PostMapping("/patient/validate")
    public String validate(@Valid @ModelAttribute("patient") PatientModel patientBean,
                           BindingResult result, RedirectAttributes redirAttrs) {
        try {
            if (result.hasErrors()) {
                return "patientInput";
            }

            // Cas creation Patient dejà en BDD
            if (patientBean.getId() == null && patientsProxy.checkExistPatient(patientBean).equals(Boolean.TRUE)) {
                result.rejectValue("lastName", "", REJECT_MESSAGE);
                result.rejectValue("firstName", "", REJECT_MESSAGE);
                result.rejectValue("birthDate", "", REJECT_MESSAGE);
                return "patientInput";
            }

            if (patientBean.getId() == null) {
                patientsProxy.addPatient(patientBean);
                redirAttrs.addFlashAttribute("successSaveMessage",
                        "Patient successfully added to list");
                return REDIRECT_PATIENTS;
            } else {
                patientsProxy.updatePatient(patientBean);
                redirAttrs.addFlashAttribute("successPatientUpdateMessage",
                        "Patient successfully updated");
                return "redirect:/patients/" + patientBean.getId();
            }
        } catch (FeignException e) {
            redirAttrs.addFlashAttribute("errorDeleteMessage",
                    "Error : " + e.status() + " during updating or creating patient");
            logger.error("Error to create or update \"Patient\" : {}", patientBean.getId());
            return REDIRECT_PATIENTS;
        }
    }

    @GetMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable("id") Integer id, Model model, RedirectAttributes redirAttrs) {
        try {
            patientsProxy.deletePatient(id);
            notesProxy.deleteAllPatientNotes(id);
            model.addAttribute("patients", patientsProxy.getAllPatients());
            redirAttrs.addFlashAttribute("successDeleteMessage", "Patient successfully deleted");
            return REDIRECT_PATIENTS;
        } catch (FeignException e) {
            redirAttrs.addFlashAttribute("errorDeleteMessage",
                    "Error : " + e.status() + " during deletion");
            logger.error("Error to delete \"Patient\" : {}", id);
            return REDIRECT_PATIENTS;
        }
        //return "redirect:/patients";
    }

    @GetMapping("/patients/{id}")
    public String patientNotes(@PathVariable Integer id, Model model, RedirectAttributes redirAttrs) {
        try {
            // Charge les infos du patient
            PatientModel patient = patientsProxy.getPatientById(id);
            model.addAttribute("patient", patient);
            // Charge la Liste des Notes du Patient
            List<NoteModel> listNotes = notesProxy.getNotesByPatient(id);
            model.addAttribute("listNotes", listNotes);
            // Charge le résultat Risque Diabete
            String diabetesResult = assessmentProxy.getRiskLevelByPatient(id);
            logger.info("diabetesResult : {}", diabetesResult);
            model.addAttribute("diabeteAssessment", diabetesResult);
            return "patientAssess";
        } catch (FeignException e) {
            //model.addAttribute("errorDeleteMessage", "Error Id patient does not exist");
            // reaffichage page en cours, avec rajout
            logger.error("FeignException : " + e);
            redirAttrs.addFlashAttribute("errorDeleteMessage",
                    "Error : " + e.status() + " during loading patient page");
            return REDIRECT_PATIENTS;
        }
        //return "patientAssess";
    }


}
