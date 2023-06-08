package com.mediscreen.clientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "microservice-assessment", url = "${microservice-assessment.url}")
public interface AssessmentProxy {

    @GetMapping("/assessment/{id}")
    String getRiskLevelByPatient(@PathVariable int id);

}
