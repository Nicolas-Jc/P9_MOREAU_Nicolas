package com.mediscreen.assessment.controller;


import com.mediscreen.assessment.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    @PostMapping("/assessment/id")
    public String postAssessById(@RequestBody Integer patientId) {
        return assessmentService.diabeteAssessment(patientId);

    }
}
