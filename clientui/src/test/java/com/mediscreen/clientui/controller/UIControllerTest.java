package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.beans.NoteBean;
import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.proxies.NotesProxy;
import com.mediscreen.clientui.proxies.PatientsProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = UIController.class)
public class UIControllerTest {
    PatientBean patientBean1;
    PatientBean patientBean2;
    List<PatientBean> listPatientsBean;
    NoteBean noteBean1;
    NoteBean noteBean2;
    List<NoteBean> listNoteBean;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatientsProxy patientsProxy;
    @MockBean
    private NotesProxy notesProxy;

    @BeforeEach
    void before() {
        patientBean1 = new PatientBean("lastName1", "firstName1", LocalDate.of(2001, 1, 1), "M", "1st street Miami", "11.11.11.11.11");
        patientBean2 = new PatientBean("lastName2", "firstName2", LocalDate.of(2002, 2, 2), "F", "2nd street Miami", "22.22.22.22.22");
        listPatientsBean.add(patientBean1);
        listPatientsBean.add(patientBean2);

        noteBean1 = new NoteBean("noteId1", 1, "2000-01-01", "Comment Note1");
        noteBean2 = new NoteBean("noteId2", 2, "2000-02-02", "Comment Note2");
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
        //GIVEN
        when(patientsProxy.getAllPatients()).thenReturn(listPatientsBean);

        //WHEN-THEN
        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
        ;
    }

}
