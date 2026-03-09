package com.hms.hospital.repository;

import com.hms.hospital.entity.Prescription;
import com.hms.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPatient(Patient patient);
    List<Prescription> findByPatientOrderByPrescribedDateDesc(Patient patient);
}
