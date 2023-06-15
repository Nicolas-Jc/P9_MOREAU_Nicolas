package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.beans.PatientBean;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PatientControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;
    @MockBean
    PatientsProxy patientsProxy;

    @MockBean
    NotesProxy notesProxy;
    private static PatientBean patientBean;
    private static PatientBean patientBean1;


    @BeforeEach
    public void contextLoads() {

        patientBean = new PatientBean();
        patientBean.setId(1);
        patientBean.setLastName("LastName");
        patientBean.setFirstName("FirstName");
        patientBean.setAddress("address");
        patientBean.setSex("M");
        patientBean.setPhoneNumber("0707070707");
        patientBean.setBirthDate(LocalDate.EPOCH);

        patientBean1 = new PatientBean();
        patientBean1.setId(1);
        patientBean1.setLastName("LastName");
        patientBean1.setFirstName("FirstName");
        patientBean1.setAddress("1st street New York");
        patientBean1.setSex("M");
        patientBean1.setPhoneNumber("111-222-333");
        patientBean1.setBirthDate(LocalDate.parse("2000-01-10"));

        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void showPatientsListTest() throws Exception {

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/patients"))
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().size(1))
                .andExpect(view().name("patients"))
                .andExpect(status().isOk()).andReturn();

        assertNotNull(result);
    }

    @Test
    public void showPatientFormTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/patientAdd"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().size(1))
                .andExpect(view().name("patientAdd"))
                .andExpect(status().isOk());
    }


   /* @Test
    public void deletePatientTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/patients/delete/1"))
                .andExpect(redirectedUrl("/patients"))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection());
        verify(patientsProxy, times(1)).deletePatient(1);
    }*/

    @Test
    void deletePatientTest() throws Exception {
        //ARRANGE

        //ACT
        mvc.perform(MockMvcRequestBuilders.get("/patients/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"))
        ;
        //check user delete:
        verify(patientsProxy, times(1)).deletePatient(1);
        //check notes delete:
        verify(notesProxy, times(1)).deleteAllPatientNotes(1);

    }
}
