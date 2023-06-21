package com.mediscreen.clientui.proxies;

import com.mediscreen.clientui.model.PatientModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice.patients", url = "${microservice.patients.url}")
public interface PatientsProxy {

    @GetMapping("/patients")
    List<PatientModel> getAllPatients();

    @GetMapping("/patients/{id}")
    PatientModel getPatientById(@PathVariable int id);

    @PostMapping("/patients/add")
    PatientModel addPatient(@RequestBody final PatientModel patientToAdd);

    @PutMapping("/patients")
    PatientModel updatePatient(@RequestBody PatientModel patientToUpdate);

    @DeleteMapping("/patients/delete/{id}")
    void deletePatient(@PathVariable("id") final Integer patientId);

    @PostMapping("/patients/exist")
    Boolean checkExistPatient(@RequestBody PatientModel patient);

}
