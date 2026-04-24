package com.hospital.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.payment.entity.Payment;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByPatientId(Long patientId);
    List<Payment> findByAppointmentId(Long appointmentId);
    List<Payment> findByStatus(String status);
}