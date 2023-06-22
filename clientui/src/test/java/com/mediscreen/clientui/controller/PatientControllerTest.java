package com.mediscreen.clientui.controller;


import com.mediscreen.clientui.model.NoteModel;
import com.mediscreen.clientui.model.PatientModel;
import com.mediscreen.clientui.proxies.AssessmentProxy;
import com.mediscreen.clientui.proxies.NotesProxy;
import com.mediscreen.clientui.proxies.PatientsProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatientsProxy patientsProxy;
    @MockBean
    private NotesProxy notesProxy;

    @MockBean
    private AssessmentProxy assessmentProxy;

    PatientModel patientBean1;
    PatientModel patientBean2;
    List<PatientModel> listPatientsBean;
    NoteModel noteBean1;
    NoteModel noteBean2;
    List<NoteModel> listNoteBean;

    @BeforeEach
    void setup() {
        patientBean1 = new PatientModel(1, "lastName1", "firstName1",
                LocalDate.of(2000, 1, 10), "M", "1st street New York", "111-222-333");
        patientBean2 = new PatientModel(2, "lastName2", "firstName2",
                LocalDate.of(2010, 12, 30), "F", "2nd street Miami", "444-555-999");
        listPatientsBean = new ArrayList<>();
        listPatientsBean.add(patientBean1);
        listPatientsBean.add(patientBean2);

        noteBean1 = new NoteModel("mdb1", 1, "2023-01-01", "note1");
        noteBean2 = new NoteModel("mdb2", 1, "2023-01-01", "note2");
        listNoteBean = new ArrayList<>();
        listNoteBean.add(noteBean1);
        listNoteBean.add(noteBean2);

    }

    @Test
    void homeTest() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void showPatientsListTest() throws Exception {
        when(patientsProxy.getAllPatients()).thenReturn(listPatientsBean);

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
        ;
    }

    @Test
    void deletePatientTest() throws Exception {

        mockMvc.perform(get("/patients/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"))
        ;

        verify(patientsProxy, times(1)).deletePatient(1);
        verify(notesProxy, times(1)).deleteAllPatientNotes(1);

    }

    @Test
    void showPatientForm_UpdatePatientTest() throws Exception {

        when(patientsProxy.getPatientById(1)).thenReturn(patientBean1);

        mockMvc.perform(get("/patientInput?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("patientInput"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attribute("patient", patientBean1))
        ;
        verify(patientsProxy, times(1)).getPatientById(1);
    }

    @Test
    void validateUpdatePatientTest() throws Exception {

        mockMvc.perform(post("/patient/validate")
                        .param("id", "1")
                        .param("lastName", "lastName1")
                        .param("firstName", "OtherFirstName")
                        .param("birthDate", "2000-01-10")
                        .param("sex", "M")
                        .param("address", "1st street New York")
                        .param("phoneNumber", "111-222-333")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/1"))
        ;

        ArgumentCaptor<PatientModel> patientBeanArgumentCaptor = ArgumentCaptor.forClass(PatientModel.class);
        verify(patientsProxy, times(1)).updatePatient(patientBeanArgumentCaptor.capture());
        PatientModel patientBeanCaptured = patientBeanArgumentCaptor.getValue();
        assertEquals(1, patientBeanCaptured.getId());

    }

    @Test
    void TestAddPatient() throws Exception {

        mockMvc.perform(get("/patientInput"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().size(1))
                .andExpect(view().name("patientInput"))
                .andExpect(status().isOk());
    }

    @Test
    void validateRequiredFieldsTest() throws Exception {

        mockMvc.perform(post("/patient/validate")
                        .param("lastName", "")
                        .param("firstName", "")
                        .param("birthDate", "")
                        .param("sex", "")
                        .param("address", "")
                        .param("phoneNumber", "")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("patientInput"))
                .andExpect(model().attributeErrorCount("patient", 7))
        ;
    }

    @Test
    void validateUserExistsTest() throws Exception {

        when(patientsProxy.checkExistPatient(any(PatientModel.class))).thenReturn(Boolean.TRUE);

        mockMvc.perform(post("/patient/validate")
                        .param("lastName", "lastName1")
                        .param("firstName", "")
                        .param("birthDate", "2000-01-10")
                        .param("sex", "M")
                        .param("address", "1st street New York")
                        .param("phoneNumber", "111-222-333")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("patientInput"))
                .andExpect(model().attributeErrorCount("patient", 2))
        ;

        verify(patientsProxy, never()).addPatient(any(PatientModel.class));
    }

    @Test
    void patientNotesTest() throws Exception {
        when(patientsProxy.getPatientById(1)).thenReturn(patientBean1);
        when(notesProxy.getNotesByPatient(1)).thenReturn(listNoteBean);

        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("patientAssess"))
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attribute("patient", patientBean1))
                .andExpect(model().attributeExists("listNotes"))
                .andExpect(model().attribute("listNotes", listNoteBean))
        ;
    }

}
