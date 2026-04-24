package com.hospital.payment.service;

import com.hospital.payment.dto.PaymentRequest;
import com.hospital.payment.entity.Payment;
import com.hospital.payment.repository.PaymentRepository;
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
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;
    private PaymentRequest request;

    @BeforeEach
    void setUp() {
        payment = new Payment();
        payment.setId(1L);
        payment.setPatientId(1L);
        payment.setPatientName("Aman Kumar");
        payment.setAppointmentId(1L);
        payment.setDoctorId(1L);
        payment.setDoctorName("Dr. Sharma");
        payment.setAmount(500.0);
        payment.setStatus("SUCCESS");
        payment.setPaymentMethod("UPI");

        request = new PaymentRequest();
        request.setPatientId(1L);
        request.setPatientName("Aman Kumar");
        request.setAppointmentId(1L);
        request.setDoctorId(1L);
        request.setDoctorName("Dr. Sharma");
        request.setAmount(500.0);
        request.setPaymentMethod("UPI");
    }

    // Test 1: Make Payment Success
    @Test
    void testMakePayment_Success() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment saved = paymentService.makePayment(request);

        assertNotNull(saved);
        assertEquals("SUCCESS", saved.getStatus());
        assertEquals(500.0, saved.getAmount());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    // Test 2: Get Payment By ID - Found
    @Test
    void testGetPaymentById_Found() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        Payment result = paymentService.getPaymentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(500.0, result.getAmount());
        verify(paymentRepository, times(1)).findById(1L);
    }

    // Test 3: Get Payment By ID - Not Found
    @Test
    void testGetPaymentById_NotFound() {
        when(paymentRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            paymentService.getPaymentById(99L);
        });

        assertEquals("Payment not found with id: 99", exception.getMessage());
    }

    // Test 4: Get Payments By Patient ID
    @Test
    void testGetPaymentsByPatientId_Success() {
        List<Payment> payments = Arrays.asList(payment);
        when(paymentRepository.findByPatientId(1L)).thenReturn(payments);

        List<Payment> result = paymentService.getPaymentsByPatientId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Aman Kumar", result.get(0).getPatientName());
        verify(paymentRepository, times(1)).findByPatientId(1L);
    }

    // Test 5: Get Payments By Appointment ID
    @Test
    void testGetPaymentsByAppointmentId_Success() {
        List<Payment> payments = Arrays.asList(payment);
        when(paymentRepository.findByAppointmentId(1L)).thenReturn(payments);

        List<Payment> result = paymentService.getPaymentsByAppointmentId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(paymentRepository, times(1)).findByAppointmentId(1L);
    }

    // Test 6: Get All Payments
    @Test
    void testGetAllPayments_Success() {
        List<Payment> payments = Arrays.asList(payment);
        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(paymentRepository, times(1)).findAll();
    }
}