package com.mediscreen.patient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Patient Controller Test")
public class PatientControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;
    //private Patient patient;
    //private Patient patient1;
    //private Patient patient2;
    //private List<Patient> listPatients;


    @Test
    public void getAllPatientsTest() throws Exception {

        // GIVEN
        Patient patient1 = new Patient(1, "LastName1", "FirstName1", LocalDate.of(2000, 1, 1), "M", "address1", "+33777777777");
        Patient patient2 = new Patient(2, "LastName2", "FirstName2", LocalDate.of(2001, 2, 2), "F", "address2", "+33888888888");
        List<Patient> listPatients = new ArrayList<>();
        listPatients.add(patient1);
        listPatients.add(patient2);
        // WHEN
        Mockito.when(patientService.getAllPatients()).thenReturn(listPatients);
        // THEN
        MvcResult mockMvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/patient/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mockMvcResult);
        Assertions.assertTrue(mockMvcResult.getResponse().getContentAsString().contains("birthDate"));
    }


    @Test
    public void getPatientByIdTest() throws Exception {

        // GIVEN
        Patient patient1 = new Patient(1, "LastName1", "FirstName1", LocalDate.of(2000, 1, 1), "M", "address1", "+33777777777");
        // WHEN
        Mockito.when(patientService.getPatientById(1)).thenReturn(Optional.of(patient1));

        MvcResult mockMvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/patient/get/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        // THEN
        assertNotNull(mockMvcResult);
    }

    @Test
    public void AddPatientTest() throws Exception {

        // GIVEN
        Patient patient = new Patient();
        patient.setLastName("LastName1");
        patient.setFirstName("FirstName1");
        patient.setBirthDate(LocalDate.of(2000, 1, 1));
        patient.setSex("F");
        patient.setAddress("address1");
        patient.setPhoneNumber("+33 777777777");
        // WHEN
        MvcResult mockMvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/patient/add")
                        .content(objectMapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        // THEN
        assertNotNull(mockMvcResult);
    }


    @Test
    public void TestUpdatePatient() throws Exception {

        // GIVEN
        Patient patient = new Patient();
        patient.setLastName("LastName1");
        patient.setFirstName("FirstName1");
        patient.setBirthDate(LocalDate.of(2000, 1, 1));
        patient.setSex("F");
        patient.setAddress("address1");
        patient.setPhoneNumber("+33 777777777");
        // WHEN
        MvcResult mockMvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/patient/update/1")
                        .content(objectMapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        // THEN
        assertNotNull(mockMvcResult);
    }

    @Test
    public void TestDeletePatient() throws Exception {

        // GIVEN
        // WHEN
        MvcResult mockMvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/patient/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        // THEN
        assertNotNull(mockMvcResult);
    }


}
