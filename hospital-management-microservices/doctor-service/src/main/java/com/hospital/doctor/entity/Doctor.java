package com.hospital.doctor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "doctors")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    
    @NotBlank(message = "Specialization is required")
    private String specialization;
    
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be 10 digits and start with 6-9")
    private String phone;
    
    @NotNull(message = "Experience is required")
    @Min(value = 0, message = "Experience must be at least 0")
    @Max(value = 50, message = "Experience must be less than 50")
    private Integer experience;
    
    @NotNull(message = "Consultation fee is required")
    @Min(value = 0, message = "Consultation fee must be at least 0")
    private Double consultationFee;
    
    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public String getPhone() { return phone; }
    public Integer getExperience() { return experience; }
    public Double getConsultationFee() { return consultationFee; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setExperience(Integer experience) { this.experience = experience; }
    public void setConsultationFee(Double consultationFee) { this.consultationFee = consultationFee; }
}