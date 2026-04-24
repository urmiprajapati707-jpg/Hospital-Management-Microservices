package com.hospital.payment.dto;

import jakarta.validation.constraints.*;

public class PaymentRequest {
    
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
    
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
    
    // Getters
    public Long getPatientId() { return patientId; }
    public String getPatientName() { return patientName; }
    public Long getAppointmentId() { return appointmentId; }
    public Long getDoctorId() { return doctorId; }
    public String getDoctorName() { return doctorName; }
    public Double getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }
    
    // Setters
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}