package com.mediscreen.assessment.service;

import com.mediscreen.assessment.beans.NoteBean;
import com.mediscreen.assessment.beans.PatientBean;

import com.mediscreen.assessment.model.Note;
import com.mediscreen.assessment.model.Patient;
import com.mediscreen.assessment.proxies.NotesProxy;
import com.mediscreen.assessment.proxies.PatientsProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class AssessmentServiceTest {

    PatientBean patient1;
    NoteBean note1;
    NoteBean note2;
    List<NoteBean> listNotes;
    @Mock
    private PatientsProxy patientProxy;
    @Mock
    private NotesProxy noteProxy;
    @InjectMocks
    AssessmentService assessmentService;


    @BeforeEach
    void setup() {
        //diabeteAssessServiceImpl.regexp = "Anticorps|Vertige";
        assessmentService.triggersList = Collections.singletonList("Cholestérol,Poids");
        listNotes = new ArrayList<>();
    }

    @Test
    void diabeteAssessment_Sup30Y_1TriggersTest() {
        // GIVEN
        patient1 = new PatientBean(1, "LastName", "Firstname",
                LocalDate.now().minusYears(40), "F", "TestAdress", "07.07.07.07.07");

        note1 = new NoteBean("1", 1, LocalDate.now().minusYears(10), "Note avec Poids");
        note2 = new NoteBean("2", 1, LocalDate.now().minusYears(9), "Note sans mot déclencheur");

        listNotes.add(note1);
        listNotes.add(note2);
        //when(patientProxy.getPatientById(1)).thenReturn(patient1);
        //when(noteProxy.getNotesByPatient(1)).thenReturn(listNotes);
        // WHEN
        String result = assessmentService.diabeteAssessment(1);
        // THEN
        assertEquals("Patient: firstname lastname (age 40) diabetes assessment is: None", result);
    }
}
