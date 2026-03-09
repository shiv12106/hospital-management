package com.hms.hospital.service;

import com.hms.hospital.entity.Patient;
import com.hms.hospital.entity.User;
import com.hms.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    
    @Autowired
    private PatientRepository patientRepository;
    
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    
    public Optional<Patient> getPatientById(Long id) {
        return id != null ? patientRepository.findById(id) : Optional.empty();
    }
    
    public List<Patient> getPatientsByUser(User user) {
        return patientRepository.findByUser(user);
    }
    
    public Optional<Patient> getPatientByEmail(String email) {
        return Optional.ofNullable(patientRepository.findByEmail(email));
    }
    
    public Patient savePatient(Patient patient) {
        return patient != null ? patientRepository.save(patient) : null;
    }
    
    public void deletePatient(Long id) {
        if (id != null) {
            patientRepository.deleteById(id);
        }
    }
    
    /**
     * Get patient for currently logged-in user (for patient data security)
     * Patients can only see their own data
     */
    public Optional<Patient> getCurrentPatient(String username) {
        // First try to find patient by user username
        Patient patient = patientRepository.findByUserUsername(username);
        if (patient != null) {
            return Optional.of(patient);
        }
        
        // Fallback to email lookup (for backward compatibility)
        return getPatientByEmail(username);
    }
}
