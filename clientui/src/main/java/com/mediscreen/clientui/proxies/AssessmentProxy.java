package com.mediscreen.clientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "microservice-assessment", url = "${microservice-assessment.url}")
public interface AssessmentProxy {
    @PostMapping("/assessment/id")
    String postAssessByPatientId(@RequestBody Integer patientId);

}
