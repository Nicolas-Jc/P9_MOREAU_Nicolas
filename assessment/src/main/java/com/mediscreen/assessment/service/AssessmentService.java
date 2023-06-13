package com.mediscreen.assessment.service;

import com.mediscreen.assessment.beans.NoteBean;
import com.mediscreen.assessment.beans.PatientBean;
import com.mediscreen.assessment.constant.RiskLevel;
import com.mediscreen.assessment.proxies.NotesProxy;
import com.mediscreen.assessment.proxies.PatientsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:triggers.properties")
public class AssessmentService {

    // Récupération Liste termes déclencheurs
    @Value("#{${triggers}}")
    List<String> triggersList;

    @Autowired
    private NotesProxy microserviceNotesProxy;

    @Autowired
    private PatientsProxy microservicePatientsProxy;

    public String diabeteAssessment(Integer patientId) {

        String diabeteAssessment = RiskLevel.LEVEL_0.getMessage();

        PatientBean patient = microservicePatientsProxy.getPatientById(patientId);
        // récupération ensemble des notes d'un patient
        List<NoteBean> listNotes = microserviceNotesProxy.getNotesByPatient(patientId);
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
        // Mise en forme réponse
        return "Patient: "
                + patient.getLastName()
                + " "
                + patient.getFirstName()
                + " (age " + age + ") diabetes assessment is: "
                + diabeteAssessment;
    }

    // Comptage Nb Termes déclencheur dans les notes d'un patient
    private Integer getTriggersCount(List<NoteBean> listNotes) {

        int count = 0;
        List<String> countedTriggers = new ArrayList<>();

        for (NoteBean note : listNotes) {
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
