package com.mediscreen.patient.controller;


import com.mediscreen.patient.exception.BadRequestException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import javassist.NotFoundException;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    @GetMapping("/patients")
    public List<Patient> getPatients() {
        return patientService.getAllPatients();
    }


    @GetMapping("/patients/{id}")
    public Optional<Patient> getPatient(@PathVariable int id) throws NotFoundException {
        return patientService.getPatientById(id);
    }

    @PostMapping("/patients/add")
    public Patient addPatient(@Valid @RequestBody final Patient patientToAdd) {
        return patientService.addPatient(patientToAdd);
    }

    @PutMapping("/patients")
    public Patient updatePatient(@Valid @RequestBody Patient patientToUpdate)
            throws NotFoundException, BadRequestException {
        return patientService.updatePatient(patientToUpdate);
    }

    @DeleteMapping("/patients/delete/{id}")
    public void deletePatient(@PathVariable("id") final Integer patientId) throws NotFoundException {
        patientService.deletePatient(patientId);
    }

}
