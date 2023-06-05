package com.mediscreen.clientui.proxies;

import com.mediscreen.clientui.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-patients", url = "${microservice-patients.url}")
public interface PatientsProxy {

    @GetMapping("/patients")
    List<PatientBean> getAllPatients();

    @GetMapping("/patients/{id}")
    PatientBean getPatientById(@PathVariable int id);

    @PostMapping("/patients/add")
    PatientBean addPatient(@RequestBody final PatientBean patientToAdd);

    @PutMapping("/patients")
    PatientBean updatePatient(@RequestBody PatientBean patientToUpdate);

    @DeleteMapping("/patients/delete/{id}")
    void deletePatient(@PathVariable("id") final Integer patientId);
}
