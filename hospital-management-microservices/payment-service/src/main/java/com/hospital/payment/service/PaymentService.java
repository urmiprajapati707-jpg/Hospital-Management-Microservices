package com.hospital.payment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hospital.payment.dto.PaymentRequest;
import com.hospital.payment.entity.Payment;
import com.hospital.payment.repository.PaymentRepository;
import java.util.List;

@Service
public class PaymentService {
    
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    // Make Payment
    public Payment makePayment(PaymentRequest request) {
        logger.info("Processing payment for patient: {} of amount: {}", request.getPatientName(), request.getAmount());
        
        Payment payment = new Payment();
        payment.setPatientId(request.getPatientId());
        payment.setPatientName(request.getPatientName());
        payment.setAppointmentId(request.getAppointmentId());
        payment.setDoctorId(request.getDoctorId());
        payment.setDoctorName(request.getDoctorName());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus("SUCCESS");
        
        Payment saved = paymentRepository.save(payment);
        logger.info("Payment processed successfully with id: {}", saved.getId());
        return saved;
    }
    
    // Get Payment by ID
    public Payment getPaymentById(Long id) {
        logger.info("Fetching payment with id: {}", id);
        return paymentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Payment not found with id: {}", id);
                    return new RuntimeException("Payment not found with id: " + id);
                });
    }
    
    // Get Payments by Patient ID
    public List<Payment> getPaymentsByPatientId(Long patientId) {
        logger.info("Fetching payments for patient id: {}", patientId);
        return paymentRepository.findByPatientId(patientId);
    }
    
    // Get Payments by Appointment ID
    public List<Payment> getPaymentsByAppointmentId(Long appointmentId) {
        logger.info("Fetching payments for appointment id: {}", appointmentId);
        return paymentRepository.findByAppointmentId(appointmentId);
    }
    
    // Get All Payments
    public List<Payment> getAllPayments() {
        logger.info("Fetching all payments");
        return paymentRepository.findAll();
    }
}