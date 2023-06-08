package com.mediscreen.assessment.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mediscreen.assessment.service.AssessmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = AssessmentController.class)
class AssessmentControllerTest {

    @MockBean
    private AssessmentService assessmentService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRiskLevelByPatientIdTest() throws Exception {

        // GIVEN
        when(assessmentService.diabeteAssessment(1)).thenReturn("Test Controller");

        // WHEN
        MvcResult result = mockMvc
                .perform(get("/assessment/1"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // THEN
        String stringResult = result.getResponse().getContentAsString();
        assertNotNull(stringResult);
        assertEquals("Test Controller", stringResult);

    }
/*




        // GIVEN
        when(assessmentService.diabeteAssessment(1)).thenReturn("test string");

        // WHEN
        MvcResult result = mockMvc.perform(get("/assessment/2"))
                //.contentType(MediaType.APPLICATION_JSON).content("1")
                .andExpect(status().isOk())
                .andReturn();

		*//*MvcResult mvcResult = mockMvc.perform(get("/patients/2"))
				.andExpect(status().isOk())
				.andReturn();*//*

        String stringResult = result.getResponse().getContentAsString();
        // THEN
        assertNotNull(stringResult);
        assertEquals("test string", stringResult);
    }*/

}
