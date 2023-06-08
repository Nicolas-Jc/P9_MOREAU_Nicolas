package com.mediscreen.patient.repository;

import com.mediscreen.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query("SELECT CASE "
            + "WHEN COUNT(p) > 0 THEN true"
            + " ELSE false END "
            + "FROM Patient p "
            + "WHERE p.lastName = :lastName AND p.firstName = :firstName AND p.birthDate = :birthDate")
    Boolean findByLastNameAndFirstNameAndBirthDate(final String lastName, final String firstName, final LocalDate birthDate);

/*
    Boolean findByLastNameAndFirstNameAndBirthDate(final String lastName, final String firstName, final LocalDate birthDate);
*/

}
