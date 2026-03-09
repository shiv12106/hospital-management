package com.hms.hospital.service;

import com.hms.hospital.entity.Doctor;
import com.hms.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
    
    public Optional<Doctor> getDoctorById(Long id) {
        return id != null ? doctorRepository.findById(id) : Optional.empty();
    }
    
    public Doctor saveDoctor(Doctor doctor) {
        return doctor != null ? doctorRepository.save(doctor) : null;
    }
    
    public void deleteDoctor(Long id) {
        if (id != null) {
            doctorRepository.deleteById(id);
        }
    }
    
    public Optional<Doctor> getDoctorByEmail(String email) {
        return doctorRepository.findAll().stream()
                .filter(doctor -> email.equals(doctor.getEmail()))
                .findFirst();
    }
}
