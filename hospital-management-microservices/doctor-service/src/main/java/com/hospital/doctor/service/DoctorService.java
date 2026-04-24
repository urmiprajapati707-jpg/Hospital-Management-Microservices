package com.hospital.doctor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hospital.doctor.entity.Doctor;
import com.hospital.doctor.repository.DoctorRepository;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    
    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    public Doctor saveDoctor(Doctor doctor) {
        logger.info("Saving new doctor: {}", doctor.getName());
        Doctor savedDoctor = doctorRepository.save(doctor);
        logger.info("Doctor saved successfully with id: {}", savedDoctor.getId());
        return savedDoctor;
    }
    
    public List<Doctor> getAllDoctors() {
        logger.info("Fetching all doctors");
        List<Doctor> doctors = doctorRepository.findAll();
        logger.info("Found {} doctors", doctors.size());
        return doctors;
    }
    
    public Optional<Doctor> getDoctorById(Long id) {
        logger.info("Fetching doctor with id: {}", id);
        return doctorRepository.findById(id);
    }
    
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        logger.info("Fetching doctors with specialization: {}", specialization);
        List<Doctor> doctors = doctorRepository.findBySpecialization(specialization);
        logger.info("Found {} doctors with specialization: {}", doctors.size(), specialization);
        return doctors;
    }
    
    public void deleteDoctor(Long id) {
        logger.info("Deleting doctor with id: {}", id);
        doctorRepository.deleteById(id);
        logger.info("Doctor deleted successfully with id: {}", id);
    }
}