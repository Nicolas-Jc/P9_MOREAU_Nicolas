package com.mediscreen.patient.service;


import com.mediscreen.patient.exception.BadRequestException;
import com.mediscreen.patient.exception.DataAlreadyExistException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@DisplayName("Patient Service Tests")
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    Patient patient1;
    Patient patient2;
    Patient patient3;

    List<Patient> listPatient;

    @Test
    void getAllPatientsTest() {
        // GIVEN
        patient1 = new Patient("lastName1", "firstName1", LocalDate.of(2001, 1, 1), "M", "1st street Miami", "11.11.11.11.11");
        patient2 = new Patient("lastName2", "firstName2", LocalDate.of(2002, 2, 2), "F", "2nd street Miami", "22.22.22.22.22");
        patient3 = new Patient("lastName3", "firstName3", LocalDate.of(2003, 3, 3), "F", "3nd street Miami", "33.33.33.33.33");
        listPatient = new ArrayList<>();
        listPatient.add(patient1);
        listPatient.add(patient2);
        listPatient.add(patient3);

        when(patientRepository.findAll()).thenReturn(listPatient);
        // WHEN
        List<Patient> results = patientService.getAllPatients();
        // THEN
        assertEquals(results.get(1).getLastName(), "lastName2");
        assertEquals(3, results.size());
    }

    @Test
    void getPatientByIdTest() throws NotFoundException {
        // GIVEN
        Patient patient10 = new Patient("lastName10", "firstName10", LocalDate.of(2010, 10, 10), "M", "10st street Miami", "11.11.11.11.11");
        when(patientRepository.findById(10)).thenReturn(Optional.of(patient10));
        // WHEN
        Optional<Patient> results = patientService.getPatientById(10);
        // THEN
        assertEquals(patient10, results.get());
    }

    @Test
    public void getPatientByIDButNotExistTest() {

        assertThrows(NotFoundException.class, () -> patientService.getPatientById(10));
    }

    @Test
    void addPatientTest() {
        //GIVEN
        Patient patient5 = new Patient("lastName5", "firstName5", LocalDate.of(2010, 10, 10), "M", "10st street Miami", "11.11.11.11.11");
        //WHEN
        when(patientRepository.save(patient5)).thenReturn(patient5);
        // WHEN
        Patient response = patientService.addPatient(patient5);
        // THEN
        assertEquals(patient5, response);
    }

    @Test
    public void addPatientButPatientAlreadyExistTest() {
        //GIVEN
        Patient patient6 = new Patient("lastName6", "firstName6", LocalDate.of(2010, 10, 10), "M", "10st street Miami", "11.11.11.11.11");
        //WHEN
        when(patientRepository.findByLastNameAndFirstNameAndBirthDate(patient6.getLastName(), patient6.getFirstName(), patient6.getBirthDate())).thenReturn(patient6);
        //THEN
        assertThrows(DataAlreadyExistException.class, () -> patientService.addPatient(patient6));
    }

    @Test
    void updatePatientTest() throws BadRequestException, NotFoundException {
        //GIVEN
        patient1 = new Patient("lastName1", "firstName1", LocalDate.of(2001, 1, 1), "M", "1st street Miami", "11.11.11.11.11");
        patient1.setId(1);
        when(patientRepository.save(patient1)).thenReturn(patient1);
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient1));
        // WHEN
        Patient response = patientService.updatePatient(patient1);
        // THEN
        assertEquals(patient1, response);
    }

    @Test
    public void updatePatientButIdNotExistTest() {
        assertThrows(BadRequestException.class, ()
                -> patientService.updatePatient(
                new Patient("Gravereau", "Pierre", LocalDate.now(), "F", "address", "phoneNumber")));
    }

    @Test
    void deletePatientTest() throws NotFoundException {
        //GIVEN
        patient1 = new Patient("lastName1", "firstName1", LocalDate.of(2001, 1, 1), "M", "1st street Miami", "11.11.11.11.11");
        patient1.setId(1);
        //when(patientRepository.save(patient1)).thenReturn(patient1);
        when(patientRepository.existsById(1)).thenReturn(Boolean.TRUE);
        //when(patientRepository.findById(1)).thenReturn(Optional.ofNullable(patient1));
        //when(patientRepository.save(patient1)).thenReturn(patient1);
        // WHEN
        patientService.deletePatient(1);
        // THEN
        verify(patientRepository, times(1)).deleteById(1);

    }

    @Test
    public void deletePatientButIdNotExistTest() {
        assertThrows(NotFoundException.class, () -> patientService.deletePatient(5));
    }

}
