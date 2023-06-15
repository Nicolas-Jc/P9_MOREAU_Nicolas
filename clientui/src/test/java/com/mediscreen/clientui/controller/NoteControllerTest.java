package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.beans.NoteBean;
import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.proxies.NotesProxy;
import com.mediscreen.clientui.proxies.PatientsProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@WebMvcTest(controllers = NoteControllerTest.class)
public class NoteControllerTest {

    private static NoteBean noteBean;

    private static PatientBean patientBean;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    PatientsProxy patientsProxy;
    @MockBean
    NotesProxy notesProxy;
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void contextLoads() {

        noteBean = new NoteBean();
        noteBean.setPatientId(1);
        noteBean.setDoctorNote("new note");
        UUID id = UUID.randomUUID();
        noteBean.setId(id.toString());
        noteBean.setNoteDate("2023-01-01");

        patientBean = new PatientBean();
        patientBean.setId(1);
        patientBean.setLastName("LastName");
        patientBean.setFirstName("FirstName");
        patientBean.setAddress("address");
        patientBean.setSex("M");
        patientBean.setPhoneNumber("0707070707");
        patientBean.setBirthDate(LocalDate.EPOCH);

        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Disabled
    @Test
    public void patientNotesListTest() throws Exception {

        when(patientsProxy.getPatientById(1)).thenReturn(patientBean);
        mvc.perform(MockMvcRequestBuilders.get("/patients/1"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attributeExists("listNotes"))
                .andExpect(model().size(2))
                .andExpect(view().name("patientNotesAss"))
                .andExpect(status().isOk());
    }

    /*@Test
    public void TestAddNote() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/noteAdd")
                        .param("id", noteBean.getId())
                        .param("patientId", String.valueOf(patientBean.getId())))
                .andExpect(model().attributeExists("note"))
                .andExpect(model().size(1))
                .andExpect(view().name("noteAdd"))
                .andExpect(status().isOk());

    }*/


}
