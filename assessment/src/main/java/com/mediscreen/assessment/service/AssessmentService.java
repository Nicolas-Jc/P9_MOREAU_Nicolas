package com.mediscreen.assessment.service;

import com.mediscreen.assessment.constant.Triggers;
import com.mediscreen.assessment.model.NoteModel;
import com.mediscreen.assessment.model.PatientModel;
import com.mediscreen.assessment.constant.RiskLevel;
import com.mediscreen.assessment.proxies.NotesProxy;
import com.mediscreen.assessment.proxies.PatientsProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;


@Service
public class AssessmentService {

    private static final Logger logger = LogManager.getLogger(AssessmentService.class);

    private final List<String> triggersList = Triggers.getTriggers();

    @Autowired
    private NotesProxy microserviceNotesProxy;

    @Autowired
    private PatientsProxy microservicePatientsProxy;

    public List<String> diabeteAssessment(Integer patientId) {

        String diabeteAssessment = RiskLevel.LEVEL_0.getMessage();

        if (patientId == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient Id is empty !");
        PatientModel patient = microservicePatientsProxy.getPatientById(patientId);
        if (patient == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Id not found !");

        // récupération ensemble des notes d'un patient
        List<NoteModel> listNotes = microserviceNotesProxy.getNotesByPatient(patientId);
        String sex = patient.getSex();
        int age = Period.between(patient.getBirthDate(), LocalDate.now()).getYears();

        // Comptage Nb de Termes déclencheurs
        Integer triggersTerms = getTriggersCount(listNotes);

        // ********** ALGORITHME ****************
        if (age < 30) {
            if (sex.equals("M")) {
                if (triggersTerms >= 5) {
                    diabeteAssessment = RiskLevel.LEVEL_3.getMessage();
                } else if (triggersTerms >= 3) {
                    diabeteAssessment = RiskLevel.LEVEL_2.getMessage();
                }
                // Femme
            } else {
                if (triggersTerms >= 7) {
                    diabeteAssessment = RiskLevel.LEVEL_3.getMessage();
                } else if (triggersTerms >= 4) {
                    diabeteAssessment = RiskLevel.LEVEL_2.getMessage();
                }
            }
            // age>=30
        } else {
            if (triggersTerms >= 8) {
                diabeteAssessment = RiskLevel.LEVEL_3.getMessage();
            } else if (triggersTerms >= 6) {
                diabeteAssessment = RiskLevel.LEVEL_2.getMessage();
            } else if (triggersTerms >= 2) {
                diabeteAssessment = RiskLevel.LEVEL_1.getMessage();
            }
        }

        diabeteAssessment = (diabeteAssessment != null) ? diabeteAssessment : RiskLevel.LEVEL_0.getMessage();

        List<String> stringResult = new ArrayList<>();

        String strResult = "Patient: " + patient.getLastName() + " " + patient.getFirstName()
                + " (age " + age + ") diabetes assessment is:";

        stringResult.add(strResult);
        stringResult.add(diabeteAssessment);

        return stringResult;
    }

    private Integer getTriggersCount(List<NoteModel> listNotes) {

        int count = 0;
        // Liste intermédiaire afin de ne pas compter 2 fois un même Trigger présent sur la même Note
        // ou sur d'autres Notes du patient
        List<String> countedTriggers = new ArrayList<>();
        logger.info("triggersList : {}", triggersList);

        for (NoteModel note : listNotes) {
            for (String trigger : triggersList) {
                if (note.getDoctorNote().toLowerCase().contains(trigger.toLowerCase())
                        && !countedTriggers.contains(trigger.toLowerCase())) {
                    count++;
                    countedTriggers.add(trigger.toLowerCase());
                }
            }
        }
        return count;
    }

}

