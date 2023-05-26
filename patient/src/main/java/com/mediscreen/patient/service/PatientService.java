package com.mediscreen.patient.service;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private static final Logger logger = LogManager.getLogger(PatientService.class);

    private final PatientRepository patientRepository;


    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Optional<Patient> getPatientById(int patientId) {

        logger.debug("Service: getPatientById - called");
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Id patient : " + patientId + " does not exist");
        return patient;
    }

    public List<Patient> getAllPatients() {

        logger.debug("Service: getAllPatients - called");
        List<Patient> patients = patientRepository.findAll();
        logger.debug("Service: getAllPatients - succes");
        return patients;
    }

    public Patient addPatient(Patient patientToAdd) {
        logger.debug("Service : add Patient - called");

        Boolean patientToCheck = patientRepository.findByLastNameAndFirstNameAndBirthDate(
                patientToAdd.getLastName(),
                patientToAdd.getFirstName(),
                patientToAdd.getBirthDate());

        if (patientToCheck.equals(Boolean.TRUE)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The patient "
                    + patientToAdd.getFirstName()
                    + " / " + patientToAdd.getBirthDate() + " already exists");
        }

        Patient patientToSave = patientRepository.save(patientToAdd);
        logger.debug("Service : addPatient - succes");
        return patientToSave;
    }

    public Patient updatePatient(Patient patient) {

        logger.debug("Service : update Patient - supply");
        if (patient.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient Id is empty !");

        Patient patientToUpdate = patientRepository.findById(patient.getId()).orElse(null);
        if (patientToUpdate == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Id : " + patient.getId() + " cannot be found");

        }

        logger.info("Service : update Patient - check");
        return patientRepository.save(patient);
    }

    public void deletePatient(int patientId) {

        logger.debug("Service : delete Patient - supply");

        if (!patientRepository.existsById(patientId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deletion impossible, patient id : "
                    + patientId + " cannot be found");
        patientRepository.deleteById(patientId);
        logger.info("Service : delete Patient - check");
    }
}
