package com.hms.hospital.repository;

import com.hms.hospital.entity.Patient;
import com.hms.hospital.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByUser(User user);
    Patient findByEmail(String email);
    Patient findByUserUsername(String username);
}
