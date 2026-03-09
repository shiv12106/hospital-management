package com.hms.hospital.service;

import com.hms.hospital.entity.Patient;
import com.hms.hospital.entity.Prescription;
import com.hms.hospital.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {
    
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }
    
    public Optional<Prescription> getPrescriptionById(Long id) {
        return id != null ? prescriptionRepository.findById(id) : Optional.empty();
    }
    
    public List<Prescription> getPrescriptionsByPatient(Patient patient) {
        return prescriptionRepository.findByPatient(patient);
    }
    
    public List<Prescription> getPrescriptionsByPatientOrderByDate(Patient patient) {
        return prescriptionRepository.findByPatientOrderByPrescribedDateDesc(patient);
    }
    
    public Prescription savePrescription(Prescription prescription) {
        if (prescription.getPrescribedDate() == null) {
            prescription.setPrescribedDate(LocalDate.now());
        }
        return prescriptionRepository.save(prescription);
    }
    
    public void deletePrescription(Long id) {
        if (id != null) {
            prescriptionRepository.deleteById(id);
        }
    }
    
    /**
     * Create a new prescription (doctor-only functionality)
     */
    public Prescription createPrescription(String medicineName, String dosage, 
                                         String frequency, String duration, 
                                         Long patientId, Long doctorId, String notes) {
        // This would be implemented in the controller with proper validation
        return null; // Placeholder - implementation in controller
    }
}
