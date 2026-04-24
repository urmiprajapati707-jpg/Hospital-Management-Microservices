package com.hospital.patient.service;

import com.hospital.patient.entity.Patient;
import com.hospital.patient.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setName("Test Patient");
        patient.setEmail("test@test.com");
        patient.setPhone("9876543210");
        patient.setPassword("123456");
        patient.setAge(25);
    }

    // Test 1: Register Patient Success
    @Test
    void testRegisterPatient_Success() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient savedPatient = patientService.registerPatient(patient);

        assertNotNull(savedPatient);
        assertEquals("Test Patient", savedPatient.getName());
        assertEquals("test@test.com", savedPatient.getEmail());
        verify(patientRepository, times(1)).save(patient);
    }

    // Test 2: Get All Patients
    @Test
    void testGetAllPatients_Success() {
        List<Patient> patients = Arrays.asList(patient);
        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> result = patientService.getAllPatients();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Patient", result.get(0).getName());
        verify(patientRepository, times(1)).findAll();
    }

    // Test 3: Get Patient By ID - Found
    @Test
    void testGetPatientById_Found() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        Patient result = patientService.getPatientById(1L);

        assertNotNull(result);
        assertEquals("Test Patient", result.getName());
        verify(patientRepository, times(1)).findById(1L);
    }

    // Test 4: Get Patient By ID - Not Found
    @Test
    void testGetPatientById_NotFound() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            patientService.getPatientById(99L);
        });

        assertEquals("Patient not found with id: 99", exception.getMessage());
        verify(patientRepository, times(1)).findById(99L);
    }
}