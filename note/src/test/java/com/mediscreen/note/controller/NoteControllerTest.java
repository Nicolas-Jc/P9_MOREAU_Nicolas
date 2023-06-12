package com.mediscreen.note.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.note.model.Note;
import com.mediscreen.note.repository.NoteRepository;
import com.mediscreen.note.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Note Controller Tests")
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NoteRepository patientRepository;

    @MockBean
    private NoteService noteService;
    @Autowired
    private ObjectMapper objectMapper;

    Note note1;
    Note note2;
    Note note3;

    List<Note> listNote;

    @BeforeEach
    void setup() {
        note1 = new Note("noteId1", 1, "2000-01-01", "Comment Note1");
        note2 = new Note("noteId2", 2, "2000-02-02", "Comment Note2");
        note3 = new Note("noteId3", 3, "2000-03-03", "Comment Note3");

        listNote = new ArrayList<>();
        listNote.add(note1);
        listNote.add(note2);
        listNote.add(note3);
    }

    @Test
    void getNotesTest() throws Exception {
        //GIVEN
        when(noteService.getAllNotes()).thenReturn(listNote);
        //WHEN
        //THEN
        MvcResult mvcResult = mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andReturn();

        List<Note> listNoteResult = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(), new TypeReference<>() {
        });
        assertEquals(3, listNoteResult.size());
    }

    @Test
    void getNoteTest() throws Exception {
        //GIVEN
        when(noteService.getNoteById("2")).thenReturn(Optional.of(note2));
        //WHEN
        //THEN
        MvcResult mvcResult = mockMvc.perform(get("/notes/2"))
                .andExpect(status().isOk())
                .andReturn();

        Note noteResult = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(), new TypeReference<>() {
        });
        assertEquals(2, noteResult.getPatientId());
        assertEquals("Comment Note2", noteResult.getDoctorNote());
    }

    @Test
    void getNoteByPatientTest() throws Exception {
        //GIVEN
        when(noteService.getNotesByPatientId(2)).thenReturn(listNote);
        //WHEN
        //THEN
        MvcResult mvcResult = mockMvc.perform(get("/patients/2/notes"))
                .andExpect(status().isOk())
                .andReturn();

        List<Note> noteResult = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(), new TypeReference<>() {
        });
     /*   assertEquals(2, noteResult.getPatientId());
        assertEquals("Comment Note2", noteResult.getDoctorNote());*/
        assertEquals(3, noteResult.size());

    }

    @Test
    void deleteNoteTest() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(delete("/notes/delete/noteId1"));
        //THEN
        verify(noteService, times(1)).deleteNoteById("noteId1");
    }


    @Test
    void updateNoteTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/notes")
                        .content(objectMapper.writeValueAsString(note1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult);
    }

    @Disabled
    @Test
    void TestAddNote() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/notes/add")
                        .content(objectMapper.writeValueAsString(note1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertNotNull(mvcResult);
    }

}
