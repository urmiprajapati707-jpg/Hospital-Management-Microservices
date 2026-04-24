package com.hospital.appointment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hospital.appointment.dto.AppointmentRequest;
import com.hospital.appointment.entity.Appointment;
import com.hospital.appointment.repository.AppointmentRepository;
import java.util.List;

@Service
public class AppointmentService {
    
    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    // Book Appointment
    public Appointment bookAppointment(AppointmentRequest request) {
        logger.info("Booking appointment for patient: {} with doctor: {}", request.getPatientName(), request.getDoctorName());
        
        Appointment appointment = new Appointment();
        appointment.setPatientId(request.getPatientId());
        appointment.setPatientName(request.getPatientName());
        appointment.setDoctorId(request.getDoctorId());
        appointment.setDoctorName(request.getDoctorName());
        appointment.setSpecialization(request.getSpecialization());
        appointment.setConsultationFee(request.getConsultationFee());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setStatus("BOOKED");
        
        Appointment saved = appointmentRepository.save(appointment);
        logger.info("Appointment booked successfully with id: {}", saved.getId());
        return saved;
    }
    
    // Get Appointment by ID
    public Appointment getAppointmentById(Long id) {
        logger.info("Fetching appointment with id: {}", id);
        return appointmentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Appointment not found with id: {}", id);
                    return new RuntimeException("Appointment not found with id: " + id);
                });
    }
    
    // Get Appointments by Patient ID
    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        logger.info("Fetching appointments for patient id: {}", patientId);
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        logger.info("Found {} appointments for patient id: {}", appointments.size(), patientId);
        return appointments;
    }
    
    // Get Appointments by Doctor ID
    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        logger.info("Fetching appointments for doctor id: {}", doctorId);
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        logger.info("Found {} appointments for doctor id: {}", appointments.size(), doctorId);
        return appointments;
    }
    
    // Cancel Appointment
    public Appointment cancelAppointment(Long id) {
        logger.info("Cancelling appointment with id: {}", id);
        Appointment appointment = getAppointmentById(id);
        appointment.setStatus("CANCELLED");
        Appointment cancelled = appointmentRepository.save(appointment);
        logger.info("Appointment cancelled successfully with id: {}", id);
        return cancelled;
    }
    
    // Get All Appointments
    public List<Appointment> getAllAppointments() {
        logger.info("Fetching all appointments");
        List<Appointment> appointments = appointmentRepository.findAll();
        logger.info("Found {} total appointments", appointments.size());
        return appointments;
    }
}