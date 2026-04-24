package com.hospital.doctor.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hospital.doctor.entity.Doctor;
import com.hospital.doctor.service.DoctorService;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    
    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);
    
    @Autowired
    private DoctorService doctorService;
    
    @PostMapping("/add")
    public ResponseEntity<Doctor> addDoctor(@Valid @RequestBody Doctor doctor) {
        logger.info("Received request to add doctor: {}", doctor.getName());
        Doctor savedDoctor = doctorService.saveDoctor(doctor);
        logger.info("Doctor added successfully with id: {}", savedDoctor.getId());
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        logger.info("Received request to get all doctors");
        List<Doctor> doctors = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        logger.info("Received request to get doctor with id: {}", id);
        Doctor doctor = doctorService.getDoctorById(id)
                .orElseThrow(() -> {
                    logger.error("Doctor not found with id: {}", id);
                    return new RuntimeException("Doctor not found with id: " + id);
                });
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }
    
    @GetMapping("/specialization/{type}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialization(@PathVariable String type) {
        logger.info("Received request to get doctors with specialization: {}", type);
        List<Doctor> doctors = doctorService.getDoctorsBySpecialization(type);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        logger.info("Received request to delete doctor with id: {}", id);
        doctorService.deleteDoctor(id);
        logger.info("Doctor deleted successfully with id: {}", id);
        return new ResponseEntity<>("Doctor deleted successfully", HttpStatus.OK);
    }
}