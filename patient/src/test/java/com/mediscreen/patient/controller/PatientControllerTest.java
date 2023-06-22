package com.mediscreen.patient.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Patient Controller Tests")
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private PatientService patientService;
    @Autowired
    private ObjectMapper objectMapper;

    Patient patient1;
    Patient patient2;
    Patient patient3;

    List<Patient> listPatient;

    @BeforeEach
    void setup() {
        patient1 = new Patient("lastName1", "firstName1", LocalDate.of(2001, 1, 1), "M", "1st street Miami", "11.11.11.11.11");
        patient2 = new Patient("lastName2", "firstName2", LocalDate.of(2002, 2, 2), "F", "2nd street Miami", "22.22.22.22.22");
        patient3 = new Patient("lastName3", "firstName3", LocalDate.of(2003, 3, 3), "F", "3nd street Miami", "33.33.33.33.33");
        listPatient = new ArrayList<>();
        listPatient.add(patient1);
        listPatient.add(patient2);
        listPatient.add(patient3);

    }

    @Test
    void getPatientsTest() throws Exception {
        //GIVEN
        when(patientService.getAllPatients()).thenReturn(listPatient);
        //WHEN
        //THEN
        MvcResult mvcResult = mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andReturn();

        List<Patient> listPatientResult = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(), new TypeReference<>() {
        });
        assertEquals(3, listPatientResult.size());
    }

    @Test
    void getPatientTest() throws Exception {
        //GIVEN
        when(patientService.getPatientById(2)).thenReturn(Optional.of(patient2));
        //WHEN
        //THEN
        MvcResult mvcResult = mockMvc.perform(get("/patients/2"))
                .andExpect(status().isOk())
                .andReturn();

        Patient patientResult = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(), new TypeReference<>() {
        });
        assertEquals("lastName2", patientResult.getLastName());
        assertEquals("firstName2", patientResult.getFirstName());
    }

    @Test
    void deletePatientTest() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(delete("/patients/delete/1"));
        //THEN
        verify(patientService, times(1)).deletePatient(1);
    }


    @Test
    void updatePatientTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/patients")
                        .content(objectMapper.writeValueAsString(patient1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult);
    }


}
