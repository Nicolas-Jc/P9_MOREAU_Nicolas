package com.mediscreen.assessment.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.mediscreen.assessment.service.AssessmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        List<String> strResult = new ArrayList<>();
        strResult.add("Patient: LastName FirstName (age 40) diabetes assessment is:");
        strResult.add("None");
        when(assessmentService.diabeteAssessment(1)).thenReturn(strResult);

        // WHEN
        MvcResult result = mockMvc
                .perform(get("/assessment/1"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // THEN
        String stringResult = result.getResponse().getContentAsString();

        assertNotNull(stringResult);
        assertEquals("[\"Patient: LastName FirstName (age 40) diabetes assessment is:\",\"None\"]", stringResult);
    }

}
