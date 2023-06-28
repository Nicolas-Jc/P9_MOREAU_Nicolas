package com.mediscreen.clientui.controller;


import com.mediscreen.clientui.model.NoteModel;
import com.mediscreen.clientui.model.PatientModel;
import com.mediscreen.clientui.proxies.NotesProxy;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NotesProxy notesProxy;

    PatientModel patientBean1;
    PatientModel patientBean2;
    List<PatientModel> listPatientsBean;
    NoteModel noteBean1;
    NoteModel noteBean2;
    List<NoteModel> listNoteBean;

    @BeforeEach
    void setup() {
        patientBean1 = new PatientModel(1, "lastName1", "fistName1",
                LocalDate.of(2000, 1, 10), "M", "1st street New York", "111-222-333");
        patientBean2 = new PatientModel(2, "lastName2", "firstName2",
                LocalDate.of(2010, 12, 30), "F", "2nd street Miami", "444-555-999");
        listPatientsBean = new ArrayList<>();
        listPatientsBean.add(patientBean1);
        listPatientsBean.add(patientBean2);

        noteBean1 = new NoteModel("mdb1", 1, "2023-06-15", "note1");
        noteBean2 = new NoteModel("mdb2", 1, "2023-10-10", "note2");
        listNoteBean = new ArrayList<>();
        listNoteBean.add(noteBean1);
        listNoteBean.add(noteBean2);

    }

    @Test
    void showNoteFormUpdatePatient() throws Exception {
        NoteModel expectedNoteBean = new NoteModel("mongoid", 1, "existing note text");
        when(notesProxy.getNote("mongoid")).thenReturn(expectedNoteBean);

        mockMvc.perform(get("/noteInput?patientId=1&id=mongoid"))
                .andExpect(status().isOk())
                .andExpect(view().name("noteInput"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("note"))
                .andExpect(model().attribute("note", expectedNoteBean)) //must be equal to expectedNoteBean
        ;

    }

    @Test
    void validateUpdateNoteTest() throws Exception {

        mockMvc.perform(post("/noteInput")
                        .param("id", "mdb2")
                        .param("patientId", "1")
                        .param("noteDate", "2023-10-10")
                        .param("doctorNote", "note2")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/1"))
        ;

        ArgumentCaptor<NoteModel> noteBeanArgumentCaptor = ArgumentCaptor.forClass(NoteModel.class);
        verify(notesProxy, times(1)).updateNote(noteBeanArgumentCaptor.capture());
        NoteModel noteBeanCaptured = noteBeanArgumentCaptor.getValue();
        assertEquals("mdb2", noteBeanCaptured.getId());

    }

    @Test
    void validateNewNoteTest() throws Exception {
        noteBean1.setId("null");

        mockMvc.perform(post("/noteInput")
                        .param("patientId", "1")
                        .param("noteDate", "2023-06-15")
                        .param("doctorNote", "note1")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/1"))
        ;

        ArgumentCaptor<NoteModel> noteBeanArgumentCaptor = ArgumentCaptor.forClass(NoteModel.class);
        verify(notesProxy, times(1)).addNote(noteBeanArgumentCaptor.capture());
        NoteModel noteBeanCaptured = noteBeanArgumentCaptor.getValue();
        assertNull(noteBeanCaptured.getId());
    }

    @Test
    void ShowNoteFormWithoutIdTest() throws Exception {

        mockMvc.perform(post("/noteInput")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("noteInput"))
                .andExpect(model().attributeErrorCount("note", 2))
        ;
    }

    @Test
    void deleteNoteTest() throws Exception {

        mockMvc.perform(post("/patients/1/notes/delete/mdb1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/1"))
        ;
        verify(notesProxy, times(1)).deleteNote("mdb1");

    }
}
