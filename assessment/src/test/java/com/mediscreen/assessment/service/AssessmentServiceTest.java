package com.mediscreen.assessment.service;

import com.mediscreen.assessment.model.NoteModel;
import com.mediscreen.assessment.model.PatientModel;


import com.mediscreen.assessment.proxies.NotesProxy;
import com.mediscreen.assessment.proxies.PatientsProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class AssessmentServiceTest {

    PatientModel patient1;
    NoteModel note1;
    NoteModel note2;
    List<NoteModel> listNotes = new ArrayList<>();

    @Mock
    private PatientsProxy patientProxy;
    @Mock
    private NotesProxy noteProxy;
    @Spy
    @InjectMocks
    AssessmentService assessmentService;


    @Test
    void diabeteAssessment_Sup30Y_1TriggerTest() {

        patient1 = new PatientModel(1, "LastName", "FirstName",
                LocalDate.now(Clock.systemUTC()).minusYears(40), "F", "TestAdress", "07.07.07.07.07");


        note1 = new NoteModel("1", 1, "2000-01-01", "Note avec Microalbumine");
        note2 = new NoteModel("2", 1, "2002-02-02", "Note sans mot déclencheur");

        listNotes.add(note1);
        listNotes.add(note2);
        when(patientProxy.getPatientById(1)).thenReturn(patient1);
        when(noteProxy.getNotesByPatient(1)).thenReturn(listNotes);

        List<String> result = assessmentService.diabeteAssessment(1);
        assertEquals("Patient: LastName FirstName (age 40) diabetes assessment is:", result.get(0));
        assertEquals("None", result.get(1));

    }

    @Test
    void diabeteAssessment_Sup30Y_2TriggersTest() {
        patient1 = new PatientModel(1, "LastName", "FirstName",
                LocalDate.now().minusYears(31), "M", "address", "07.07.07.07.07");
        note1 = new NoteModel("1", 1, "2000-01-01", "Note avec Microalbumine Taille");
        note2 = new NoteModel("2", 1, "2002-02-02", "Note sans mot déclencheur");
        listNotes.add(note1);
        listNotes.add(note2);
        when(patientProxy.getPatientById(1)).thenReturn(patient1);
        when(noteProxy.getNotesByPatient(1)).thenReturn(listNotes);

        List<String> result = assessmentService.diabeteAssessment(1);
        assertEquals("Patient: LastName FirstName (age 31) diabetes assessment is:", result.get(0));
        assertEquals("Borderline", result.get(1));
    }

    @Test
    void diabeteAssessment_Sup30Y_6TriggersTest() {
        patient1 = new PatientModel(1, "LastName", "FirstName",
                LocalDate.now().minusYears(35), "M", "address", "07.07.07.07.07");
        note1 = new NoteModel("1", 1, "2000-01-01", "Note avec Microalbumine Taille Poids");
        note2 = new NoteModel("2", 1, "2002-02-02", "Note Fumeur Anormal Cholestérol");
        listNotes.add(note1);
        listNotes.add(note2);
        when(patientProxy.getPatientById(1)).thenReturn(patient1);
        when(noteProxy.getNotesByPatient(1)).thenReturn(listNotes);

        List<String> result = assessmentService.diabeteAssessment(1);
        assertEquals("Patient: LastName FirstName (age 35) diabetes assessment is:", result.get(0));
        assertEquals("In Danger", result.get(1));
    }

    @Test
    void diabeteAssessment_Sup30Y_8TriggersTest() {
        patient1 = new PatientModel(1, "LastName", "FirstName",
                LocalDate.now().minusYears(38), "M", "address", "07.07.07.07.07");
        note1 = new NoteModel("1", 1, "2000-01-01", "Note avec Microalbumine Taille Poids");
        note2 = new NoteModel("2", 1, "2002-02-02", "Note Fumeur Anormal Cholestérol Rechute Réaction");
        listNotes.add(note1);
        listNotes.add(note2);
        when(patientProxy.getPatientById(1)).thenReturn(patient1);
        when(noteProxy.getNotesByPatient(1)).thenReturn(listNotes);

        List<String> result = assessmentService.diabeteAssessment(1);
        assertEquals("Patient: LastName FirstName (age 38) diabetes assessment is:", result.get(0));
        assertEquals("Early onset", result.get(1));
    }

    @Test
    void diabeteAssessment_FemaleInf30Y_2TriggersTest() {
        patient1 = new PatientModel(1, "LastName", "FirstName",
                LocalDate.now().minusYears(29), "F", "address", "07.07.07.07.07");
        note1 = new NoteModel("1", 1, "2000-01-01", "Note avec Microalbumine");
        note2 = new NoteModel("2", 1, "2002-02-02", "Note Fumeur");
        listNotes.add(note1);
        listNotes.add(note2);
        when(patientProxy.getPatientById(1)).thenReturn(patient1);
        when(noteProxy.getNotesByPatient(1)).thenReturn(listNotes);

        List<String> result = assessmentService.diabeteAssessment(1);
        assertEquals("Patient: LastName FirstName (age 29) diabetes assessment is:", result.get(0));
        assertEquals("None", result.get(1));
    }

    @Test
    void diabeteAssessment_FemaleInf30Y_4TriggersTest() {
        patient1 = new PatientModel(1, "LastName", "FirstName",
                LocalDate.now().minusYears(25), "F", "address", "07.07.07.07.07");
        note1 = new NoteModel("1", 1, "2000-01-01", "Note avec Microalbumine Réaction");
        note2 = new NoteModel("2", 1, "2002-02-02", "Note Fumeur Rechute ");
        listNotes.add(note1);
        listNotes.add(note2);
        when(patientProxy.getPatientById(1)).thenReturn(patient1);
        when(noteProxy.getNotesByPatient(1)).thenReturn(listNotes);

        List<String> result = assessmentService.diabeteAssessment(1);
        assertEquals("Patient: LastName FirstName (age 25) diabetes assessment is:", result.get(0));
        assertEquals("In Danger", result.get(1));
    }

    @Test
    void diabeteAssessment_FemaleInf30_7TriggersTest() {
        patient1 = new PatientModel(1, "LastName", "FirstName",
                LocalDate.now().minusYears(23), "F", "address", "07.07.07.07.07");
        note1 = new NoteModel("1", 1, "2000-01-01", "Note avec Microalbumine Réaction Vertige");
        note2 = new NoteModel("2", 1, "2002-02-02", "Note Fumeur Rechute Anticorps Hémoglobine A1C");
        listNotes.add(note1);
        listNotes.add(note2);
        when(patientProxy.getPatientById(1)).thenReturn(patient1);
        when(noteProxy.getNotesByPatient(1)).thenReturn(listNotes);

        List<String> result = assessmentService.diabeteAssessment(1);
        assertEquals("Patient: LastName FirstName (age 23) diabetes assessment is:", result.get(0));
        assertEquals("Early onset", result.get(1));
    }

    @Test
    void diabeteAssessment_MaleInf30Y_2TriggersTest() {
        patient1 = new PatientModel(1, "LastName", "FirstName",
                LocalDate.now().minusYears(28), "M", "address1", "111-222-333");
        note1 = new NoteModel("1", 1, "2000-01-01", "Note avec Microalbumine");
        note2 = new NoteModel("2", 1, "2002-02-02", "Note Hémoglobine A1C");
        listNotes.add(note1);
        listNotes.add(note2);
        when(patientProxy.getPatientById(1)).thenReturn(patient1);
        when(noteProxy.getNotesByPatient(1)).thenReturn(listNotes);

        List<String> result = assessmentService.diabeteAssessment(1);
        assertEquals("Patient: LastName FirstName (age 28) diabetes assessment is:", result.get(0));
        assertEquals("None", result.get(1));
    }

    @Test
    void diabeteAssessment_MaleInf30Y_3TriggersTest() {
        patient1 = new PatientModel(1, "LastName", "FirstName",
                LocalDate.now().minusYears(20), "M", "address1", "07.07.07.07.07");
        note1 = new NoteModel("1", 1, "2000-01-01", "Note avec Microalbumine");
        note2 = new NoteModel("2", 1, "2002-02-02", "Note Anticorps Hémoglobine A1C");
        listNotes.add(note1);
        listNotes.add(note2);
        when(patientProxy.getPatientById(1)).thenReturn(patient1);
        when(noteProxy.getNotesByPatient(1)).thenReturn(listNotes);

        List<String> result = assessmentService.diabeteAssessment(1);
        assertEquals("Patient: LastName FirstName (age 20) diabetes assessment is:", result.get(0));
        assertEquals("In Danger", result.get(1));
    }

    @Test
    void diabeteAssessment_MaleInf30Y_5TriggersTest() {
        patient1 = new PatientModel(1, "LastName", "FirstName",
                LocalDate.now().minusYears(25), "M", "address1", "07.07.07.07.07");
        note1 = new NoteModel("1", 1, "2000-01-01", "Note avec Microalbumine Poids Fumeur");
        note2 = new NoteModel("2", 1, "2002-02-02", "Note Anticorps Hémoglobine A1C");
        listNotes.add(note1);
        listNotes.add(note2);
        when(patientProxy.getPatientById(1)).thenReturn(patient1);
        when(noteProxy.getNotesByPatient(1)).thenReturn(listNotes);

        List<String> result = assessmentService.diabeteAssessment(1);
        assertEquals("Patient: LastName FirstName (age 25) diabetes assessment is:", result.get(0));
        assertEquals("Early onset", result.get(1));
    }

}
