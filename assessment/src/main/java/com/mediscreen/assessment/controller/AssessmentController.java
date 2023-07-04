package com.mediscreen.assessment.controller;


import com.mediscreen.assessment.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    @GetMapping("/assessment/{id}")
    public List<String> getRiskLevelByPatientId(@PathVariable int id) {
        return assessmentService.diabeteAssessment(id);

    }

}
