package com.hospital.payment.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hospital.payment.dto.PaymentRequest;
import com.hospital.payment.entity.Payment;
import com.hospital.payment.service.PaymentService;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    
    @Autowired
    private PaymentService paymentService;
    
    @PostMapping("/pay")
    public ResponseEntity<Payment> makePayment(@Valid @RequestBody PaymentRequest request) {
        logger.info("Received payment request for patient: {} amount: {}", request.getPatientName(), request.getAmount());
        Payment payment = paymentService.makePayment(request);
        logger.info("Payment completed successfully with id: {}", payment.getId());
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        logger.info("Received request to get payment with id: {}", id);
        Payment payment = paymentService.getPaymentById(id);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
    
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Payment>> getPaymentsByPatientId(@PathVariable Long patientId) {
        logger.info("Received request to get payments for patient id: {}", patientId);
        List<Payment> payments = paymentService.getPaymentsByPatientId(patientId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
    
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<Payment>> getPaymentsByAppointmentId(@PathVariable Long appointmentId) {
        logger.info("Received request to get payments for appointment id: {}", appointmentId);
        List<Payment> payments = paymentService.getPaymentsByAppointmentId(appointmentId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAllPayments() {
        logger.info("Received request to get all payments");
        List<Payment> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
}