package com.mediscreen.assessment.proxies;

import com.mediscreen.assessment.model.PatientModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microservice-patients", url = "${microservice-patients.url}")
public interface PatientsProxy {

    @GetMapping("/patients/{id}")
    PatientModel getPatientById(@PathVariable int id);

}
