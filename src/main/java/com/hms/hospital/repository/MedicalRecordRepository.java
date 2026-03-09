package com.hms.hospital.repository;

import com.hms.hospital.entity.MedicalRecord;
import com.hms.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPatient(Patient patient);
    List<MedicalRecord> findByPatientOrderByRecordDateDesc(Patient patient);
    List<MedicalRecord> findByDoctorId(Long doctorId);
}
