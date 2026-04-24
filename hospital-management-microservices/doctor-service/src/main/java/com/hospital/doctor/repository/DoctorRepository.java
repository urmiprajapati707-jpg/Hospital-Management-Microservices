package com.hospital.doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.doctor.entity.Doctor;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecialization(String specialization);
}