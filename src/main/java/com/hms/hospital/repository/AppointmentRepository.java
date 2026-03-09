package com.hms.hospital.repository;

import com.hms.hospital.entity.Appointment;
import com.hms.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient(Patient patient);
    List<Appointment> findByPatientIn(List<Patient> patients);
}
