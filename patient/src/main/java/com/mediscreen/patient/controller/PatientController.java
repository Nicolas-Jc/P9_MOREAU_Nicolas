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

   /* @GetMapping("/get/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable long id) {

        Optional<Patient> patient = patientService.getPatientById(id);
        if (patient.isPresent()) {
            return new ResponseEntity<>(patient.get(), HttpStatus.OK);
        } else {
            throw new DataNotFoundException("Patient doesn't exist");

        }
    }*/

    @GetMapping("/patients/{id}")
    public Optional<Patient> getPatient(@PathVariable int id) throws NotFoundException {
        /* if (patient.isEmpty())
            throw new NotFoundException("The Id patient" + id + "does not exist");*/
        return patientService.getPatientById(id);
    }

    @PostMapping("/patients/add")
    public Patient addPatient(@Valid @RequestBody final Patient patientToAdd) {

        return patientService.addPatient(patientToAdd);
    }

   /* @PostMapping("/update/{id}")
    public Patient updatePatient(@PathVariable("id") Integer patientId, @Valid @RequestBody Patient patientRequest) {

        return patientService.updatePatient(patientId, patientRequest);
    }*/

    @PutMapping(value = "/patients")
    public Patient updatePatient(@Valid @RequestBody Patient patientToUpdate)
            throws NotFoundException, BadRequestException {

        return patientService.updatePatient(patientToUpdate);
    }

    @DeleteMapping("/patients/delete/{id}")
    public void deletePatient(@PathVariable("id") final Integer patientId) throws NotFoundException {

        patientService.deletePatient(patientId);
    }


}
