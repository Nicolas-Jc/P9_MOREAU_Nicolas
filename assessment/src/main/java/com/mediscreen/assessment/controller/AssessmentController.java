package com.mediscreen.assessment.controller;


import com.mediscreen.assessment.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    //@PostMapping("/assessment/id")
    @GetMapping("/assessment/{id}")
    public String getRiskLevelByPatientId(@PathVariable int id) {
        return assessmentService.diabeteAssessment(id);

    }

}
