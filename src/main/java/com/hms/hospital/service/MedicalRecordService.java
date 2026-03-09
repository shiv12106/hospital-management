package com.hms.hospital.service;

import com.hms.hospital.entity.MedicalRecord;
import com.hms.hospital.entity.Patient;
import com.hms.hospital.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {
    
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }
    
    public Optional<MedicalRecord> getMedicalRecordById(Long id) {
        return medicalRecordRepository.findById(id);
    }
    
    public List<MedicalRecord> getMedicalRecordsByPatient(Patient patient) {
        return medicalRecordRepository.findByPatientOrderByRecordDateDesc(patient);
    }
    
    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        if (medicalRecord.getRecordDate() == null) {
            medicalRecord.setRecordDate(LocalDate.now());
        }
        return medicalRecordRepository.save(medicalRecord);
    }
    
    public void deleteMedicalRecord(Long id) {
        medicalRecordRepository.deleteById(id);
    }
    
    /**
     * Get medical records for currently logged-in patient (patient data security)
     */
    public List<MedicalRecord> getCurrentPatientMedicalRecords(String username) {
        // This would be implemented with proper patient lookup in controller
        return null; // Placeholder - implementation in controller
    }
}
