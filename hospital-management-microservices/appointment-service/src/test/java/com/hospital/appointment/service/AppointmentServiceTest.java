package com.hospital.appointment.service;

import com.hospital.appointment.dto.AppointmentRequest;
import com.hospital.appointment.entity.Appointment;
import com.hospital.appointment.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private Appointment appointment;
    private AppointmentRequest request;

    @BeforeEach
    void setUp() {
        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setPatientId(1L);
        appointment.setPatientName("Aman Kumar");
        appointment.setDoctorId(1L);
        appointment.setDoctorName("Dr. Sharma");
        appointment.setSpecialization("Cardiology");
        appointment.setConsultationFee(500.0);
        appointment.setAppointmentDate(LocalDate.of(2026, 4, 10));
        appointment.setAppointmentTime(LocalTime.of(10, 30));
        appointment.setStatus("BOOKED");

        request = new AppointmentRequest();
        request.setPatientId(1L);
        request.setPatientName("Aman Kumar");
        request.setDoctorId(1L);
        request.setDoctorName("Dr. Sharma");
        request.setSpecialization("Cardiology");
        request.setConsultationFee(500.0);
        request.setAppointmentDate(LocalDate.of(2026, 4, 10));
        request.setAppointmentTime(LocalTime.of(10, 30));
    }

    // Test 1: Book Appointment Success
    @Test
    void testBookAppointment_Success() {
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment saved = appointmentService.bookAppointment(request);

        assertNotNull(saved);
        assertEquals("BOOKED", saved.getStatus());
        assertEquals("Aman Kumar", saved.getPatientName());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    // Test 2: Get Appointment By ID - Found
    @Test
    void testGetAppointmentById_Found() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        Appointment result = appointmentService.getAppointmentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Aman Kumar", result.getPatientName());
        verify(appointmentRepository, times(1)).findById(1L);
    }

    // Test 3: Get Appointment By ID - Not Found
    @Test
    void testGetAppointmentById_NotFound() {
        when(appointmentRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.getAppointmentById(99L);
        });

        assertEquals("Appointment not found with id: 99", exception.getMessage());
    }

    // Test 4: Get Appointments By Patient ID
    @Test
    void testGetAppointmentsByPatientId_Success() {
        List<Appointment> appointments = Arrays.asList(appointment);
        when(appointmentRepository.findByPatientId(1L)).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAppointmentsByPatientId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Aman Kumar", result.get(0).getPatientName());
        verify(appointmentRepository, times(1)).findByPatientId(1L);
    }

    // Test 5: Get Appointments By Doctor ID
    @Test
    void testGetAppointmentsByDoctorId_Success() {
        List<Appointment> appointments = Arrays.asList(appointment);
        when(appointmentRepository.findByDoctorId(1L)).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAppointmentsByDoctorId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Dr. Sharma", result.get(0).getDoctorName());
        verify(appointmentRepository, times(1)).findByDoctorId(1L);
    }

    // Test 6: Cancel Appointment
    @Test
    void testCancelAppointment_Success() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment cancelled = appointmentService.cancelAppointment(1L);

        assertNotNull(cancelled);
        assertEquals("CANCELLED", cancelled.getStatus());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    // Test 7: Get All Appointments
    @Test
    void testGetAllAppointments_Success() {
        List<Appointment> appointments = Arrays.asList(appointment);
        when(appointmentRepository.findAll()).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAllAppointments();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(appointmentRepository, times(1)).findAll();
    }
}