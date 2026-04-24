package com.hospital.payment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Patient ID is required")
    private Long patientId;
    
    @NotBlank(message = "Patient name is required")
    private String patientName;
    
    @NotNull(message = "Appointment ID is required")
    private Long appointmentId;
    
    @NotNull(message = "Doctor ID is required")
    private Long doctorId;
    
    @NotBlank(message = "Doctor name is required")
    private String doctorName;
    
    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be at least 0")
    private Double amount;
    
    private String status; // SUCCESS, FAILED, PENDING
    
    private LocalDateTime paymentDate;
    
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
    
    public Payment() {
        this.paymentDate = LocalDateTime.now();
        this.status = "SUCCESS";
    }
    
    // Getters
    public Long getId() { return id; }
    public Long getPatientId() { return patientId; }
    public String getPatientName() { return patientName; }
    public Long getAppointmentId() { return appointmentId; }
    public Long getDoctorId() { return doctorId; }
    public String getDoctorName() { return doctorName; }
    public Double getAmount() { return amount; }
    public String getStatus() { return status; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public String getPaymentMethod() { return paymentMethod; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setStatus(String status) { this.status = status; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}