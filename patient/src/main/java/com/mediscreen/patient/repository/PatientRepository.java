package com.mediscreen.patient.repository;

import com.mediscreen.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    //Patient findByLastNameAndFirstNameAndBirthDate(final String lastName, final String firstName, final LocalDate birthDate);

}
