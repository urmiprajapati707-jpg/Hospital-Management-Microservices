package com.hospital.doctor.service;

import com.hospital.doctor.entity.Doctor;
import com.hospital.doctor.repository.DoctorRepository;
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
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Dr. Sharma");
        doctor.setSpecialization("Cardiology");
        doctor.setPhone("9876543210");
        doctor.setExperience(10);
        doctor.setConsultationFee(500.0);
    }

    // Test 1: Save Doctor Success
    @Test
    void testSaveDoctor_Success() {
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        Doctor savedDoctor = doctorService.saveDoctor(doctor);

        assertNotNull(savedDoctor);
        assertEquals("Dr. Sharma", savedDoctor.getName());
        assertEquals("Cardiology", savedDoctor.getSpecialization());
        verify(doctorRepository, times(1)).save(doctor);
    }

    // Test 2: Get All Doctors
    @Test
    void testGetAllDoctors_Success() {
        List<Doctor> doctors = Arrays.asList(doctor);
        when(doctorRepository.findAll()).thenReturn(doctors);

        List<Doctor> result = doctorService.getAllDoctors();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Dr. Sharma", result.get(0).getName());
        verify(doctorRepository, times(1)).findAll();
    }

    // Test 3: Get Doctor By ID - Found
    @Test
    void testGetDoctorById_Found() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        Optional<Doctor> result = doctorService.getDoctorById(1L);

        assertTrue(result.isPresent());
        assertEquals("Dr. Sharma", result.get().getName());
        verify(doctorRepository, times(1)).findById(1L);
    }

    // Test 4: Get Doctor By ID - Not Found
    @Test
    void testGetDoctorById_NotFound() {
        when(doctorRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Doctor> result = doctorService.getDoctorById(99L);

        assertFalse(result.isPresent());
        verify(doctorRepository, times(1)).findById(99L);
    }

    // Test 5: Get Doctors By Specialization
    @Test
    void testGetDoctorsBySpecialization_Success() {
        List<Doctor> doctors = Arrays.asList(doctor);
        when(doctorRepository.findBySpecialization("Cardiology")).thenReturn(doctors);

        List<Doctor> result = doctorService.getDoctorsBySpecialization("Cardiology");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Cardiology", result.get(0).getSpecialization());
        verify(doctorRepository, times(1)).findBySpecialization("Cardiology");
    }

    // Test 6: Delete Doctor
    @Test
    void testDeleteDoctor_Success() {
        doNothing().when(doctorRepository).deleteById(1L);

        doctorService.deleteDoctor(1L);

        verify(doctorRepository, times(1)).deleteById(1L);
    }
}