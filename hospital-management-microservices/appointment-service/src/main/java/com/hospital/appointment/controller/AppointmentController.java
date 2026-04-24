package com.hospital.appointment.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hospital.appointment.dto.AppointmentRequest;
import com.hospital.appointment.entity.Appointment;
import com.hospital.appointment.service.AppointmentService;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    
    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);
    
    @Autowired
    private AppointmentService appointmentService;
    
    @PostMapping("/book")
    public ResponseEntity<Appointment> bookAppointment(@Valid @RequestBody AppointmentRequest request) {
        logger.info("Received request to book appointment for patient: {}", request.getPatientName());
        Appointment appointment = appointmentService.bookAppointment(request);
        logger.info("Appointment booked successfully with id: {}", appointment.getId());
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        logger.info("Received request to get appointment with id: {}", id);
        Appointment appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }
    
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatientId(@PathVariable Long patientId) {
        logger.info("Received request to get appointments for patient id: {}", patientId);
        List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patientId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
    
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorId(@PathVariable Long doctorId) {
        logger.info("Received request to get appointments for doctor id: {}", doctorId);
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Appointment> cancelAppointment(@PathVariable Long id) {
        logger.info("Received request to cancel appointment with id: {}", id);
        Appointment appointment = appointmentService.cancelAppointment(id);
        logger.info("Appointment cancelled successfully with id: {}", id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        logger.info("Received request to get all appointments");
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
}