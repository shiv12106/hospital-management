package com.hms.hospital.service;

import com.hms.hospital.entity.Appointment;
import com.hms.hospital.entity.Patient;
import com.hms.hospital.repository.AppointmentRepository;
import com.hms.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }
    
    public List<Appointment> getAppointmentsByPatient(Patient patient) {
        return appointmentRepository.findByPatient(patient);
    }
    
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
    
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
    
    /**
     * Get appointments for currently logged-in user (for patient data security)
     * Patients can only see their own appointments
     */
    public List<Appointment> getCurrentUserAppointments(String username) {
        // Find the patient associated with this user
        Patient patient = patientRepository.findByUserUsername(username);
        if (patient != null) {
            return appointmentRepository.findByPatient(patient);
        }
        return List.of();
    }
    
    /**
     * Get current patient for the logged-in user
     */
    public Optional<Patient> getCurrentPatient(String username) {
        Patient patient = patientRepository.findByUserUsername(username);
        return Optional.ofNullable(patient);
    }
}
