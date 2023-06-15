package com.mediscreen.clientui.controller;


import com.mediscreen.clientui.beans.NoteBean;
import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.proxies.AssessmentProxy;
import com.mediscreen.clientui.proxies.NotesProxy;
import com.mediscreen.clientui.proxies.PatientsProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

    PatientBean patientBean1;
    PatientBean patientBean2;
    List<PatientBean> listPatientsBean;
    NoteBean noteBean1;
    NoteBean noteBean2;
    List<NoteBean> listNoteBean;

    @BeforeEach
    void setup() {
        patientBean1 = new PatientBean(1, "lastName1", "firstName1",
                LocalDate.of(2000, 1, 10), "M", "1st street New York", "111-222-333");
        patientBean2 = new PatientBean(2, "lastName2", "firstName2",
                LocalDate.of(2010, 12, 30), "F", "2nd street Miami", "444-555-999");
        listPatientsBean = new ArrayList<>();
        listPatientsBean.add(patientBean1);
        listPatientsBean.add(patientBean2);

        noteBean1 = new NoteBean("mdb1", 1, "2023-01-01", "note1");
        noteBean2 = new NoteBean("mdb2", 1, "2023-01-01", "note2");
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

        mockMvc.perform(get("/patientAdd?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("patientAdd"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attribute("patient", patientBean1))
        ;
        verify(patientsProxy, times(1)).getPatientById(1);
    }

    @Disabled
    @Test
    void showPatientForm_NewPatient() throws Exception {


        mockMvc.perform(get("/patientAdd"))
                .andExpect(status().isOk())
                .andExpect(view().name("patientAdd"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attribute("patient", new PatientBean())) //must be equal to empty PatientBean
        ;
        verify(patientsProxy, never()).getPatientById(any(Integer.class));

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

        ArgumentCaptor<PatientBean> patientBeanArgumentCaptor = ArgumentCaptor.forClass(PatientBean.class);
        verify(patientsProxy, times(1)).updatePatient(patientBeanArgumentCaptor.capture());
        PatientBean patientBeanCaptured = patientBeanArgumentCaptor.getValue();
        //assertEquals(patientBean1, patientBeanCaptured);
        assertEquals(1, patientBeanCaptured.getId());

    }


    @Disabled
    @Test
    void validate_NewPatientTest() throws Exception {
        //NMO NMO NMO NMO
        when(patientsProxy.checkExistPatient(any(PatientBean.class))).thenReturn(Boolean.FALSE);
        patientBean1.setId(null);

        mockMvc.perform(post("/patient/validate")
                        .param("lastName", "lastName1")
                        .param("firstName", "firstName1")
                        .param("birthDate", "2000-01-10")
                        .param("sex", "M")
                        .param("address", "1st street New York")
                        .param("phoneNumber", "111-222-333")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"))
        ;

        ArgumentCaptor<PatientBean> patientBeanArgumentCaptor = ArgumentCaptor.forClass(PatientBean.class);
        verify(patientsProxy, times(1)).addPatient(patientBeanArgumentCaptor.capture());
        PatientBean patientBeanCaptured = patientBeanArgumentCaptor.getValue();
        assertEquals(patientBean1, patientBeanCaptured);

    }

    @Test
    void TestAddPatient() throws Exception {

        mockMvc.perform(get("/patientAdd"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().size(1))
                .andExpect(view().name("patientAdd"))
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
                .andExpect(view().name("patientAdd"))
                .andExpect(model().attributeErrorCount("patient", 7))
        ;
    }

    @Test
    void validateUserExistsTest() throws Exception {

        when(patientsProxy.checkExistPatient(any(PatientBean.class))).thenReturn(Boolean.TRUE);

        mockMvc.perform(post("/patient/validate")
                        .param("lastName", "lastName1")
                        .param("firstName", "")
                        .param("birthDate", "2000-01-10")
                        .param("sex", "M")
                        .param("address", "1st street New York")
                        .param("phoneNumber", "111-222-333")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("patientAdd"))
                .andExpect(model().attributeErrorCount("patient", 2))
        ;

        verify(patientsProxy, never()).addPatient(any(PatientBean.class));
    }

    @Test
    void patientNotesTest() throws Exception {
        when(patientsProxy.getPatientById(1)).thenReturn(patientBean1);
        when(notesProxy.getNotesByPatient(1)).thenReturn(listNoteBean);

        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("patientNotesAss"))
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attribute("patient", patientBean1))
                .andExpect(model().attributeExists("listNotes"))
                .andExpect(model().attribute("listNotes", listNoteBean))
        ;
    }

}
