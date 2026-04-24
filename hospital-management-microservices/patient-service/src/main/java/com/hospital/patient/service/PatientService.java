package com.hospital.patient.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hospital.patient.entity.Patient;
import com.hospital.patient.repository.PatientRepository;
import java.util.List;

@Service
public class PatientService {
	
	
	private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
    
    @Autowired
    private PatientRepository patientRepository;
    
    // Register Patient
    public Patient registerPatient(Patient patient) {
        logger.info("Registering new patient with email: {}", patient.getEmail());
        Patient savedPatient = patientRepository.save(patient);
        logger.info("Patient registered successfully with id: {}", savedPatient.getId());
        return savedPatient;
    }

    // Get All Patients
    public List<Patient> getAllPatients() {
        logger.info("Fetching all patients");
        List<Patient> patients = patientRepository.findAll();
        logger.info("Found {} patients", patients.size());
        return patients;
    }
    
    // Get Patient by ID
    public Patient getPatientById(Long id) {
        logger.info("Fetching patient with id: {}", id);
        return patientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Patient not found with id: {}", id);
                    return new RuntimeException("Patient not found with id: " + id);
                });
    }
}