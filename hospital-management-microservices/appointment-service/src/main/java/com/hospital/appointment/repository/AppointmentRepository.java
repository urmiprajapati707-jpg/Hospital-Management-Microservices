package com.hospital.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.appointment.entity.Appointment;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByStatus(String status);
}