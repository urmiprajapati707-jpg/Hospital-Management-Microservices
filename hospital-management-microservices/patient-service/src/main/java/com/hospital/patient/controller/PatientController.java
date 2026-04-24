package com.hospital.patient.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hospital.patient.entity.Patient;
import com.hospital.patient.service.PatientService;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);
    
    @Autowired
    private PatientService service;
    
    @PostMapping("/register")
    public ResponseEntity<Patient> registerPatient(@Valid @RequestBody Patient patient) {
        System.out.println("===== TEST LOG: Register endpoint hit =====");
        logger.info("Received request to register patient: {}", patient.getEmail());
        Patient savedPatient = service.registerPatient(patient);
        logger.info("Patient registered successfully with id: {}", savedPatient.getId());
        System.out.println("===== TEST LOG: Patient registered with id: " + savedPatient.getId() + " =====");
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }
    
    @GetMapping("/all")
    public List<Patient> getAllPatients() {
        System.out.println("===== TEST LOG: Get all patients endpoint hit =====");
        logger.info("Received request to get all patients");
        return service.getAllPatients();
    }
    
    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        System.out.println("===== TEST LOG: Get patient by id endpoint hit =====");
        logger.info("Received request to get patient with id: {}", id);
        return service.getPatientById(id);
    }
}