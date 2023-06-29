package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.proxies.AssessmentProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AssessmentController {
    @Autowired
    private AssessmentProxy assessmentProxy;

    @GetMapping(value = "/assessment/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getDiabeteAssessment(@PathVariable Integer id) {
        return assessmentProxy.getRiskLevelByPatient(id);
    }

}
