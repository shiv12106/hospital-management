package com.hms.hospital.controller;

import com.hms.hospital.entity.Appointment;
import com.hms.hospital.entity.Doctor;
import com.hms.hospital.entity.Patient;
import com.hms.hospital.entity.Prescription;
import com.hms.hospital.entity.Report;
import com.hms.hospital.repository.AppointmentRepository;
import com.hms.hospital.repository.DoctorRepository;
import com.hms.hospital.repository.PatientRepository;
import com.hms.hospital.repository.PrescriptionRepository;
import com.hms.hospital.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorDashboardController {
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private ReportRepository reportRepository;
    
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    
    @GetMapping("/dashboard")
    public String getDoctorDashboard(Model model) {
        // For now, get first doctor (in production, link User to Doctor)
        List<Doctor> allDoctors = doctorRepository.findAll();
        Doctor doctor = allDoctors.isEmpty() ? null : allDoctors.get(0);
        
        if (doctor != null) {
            model.addAttribute("doctor", doctor);
            
            // Get doctor's appointments
            List<Appointment> appointments = appointmentRepository.findAll();
            List<Appointment> doctorAppointments = appointments.stream()
                    .filter(a -> a.getDoctor().getId().equals(doctor.getId()))
                    .toList();
            model.addAttribute("appointments", doctorAppointments);
            
            // Get all patients (doctor can see all patients in their department)
            List<Patient> patients = patientRepository.findAll();
            model.addAttribute("patients", patients);
            
            // Get statistics
            model.addAttribute("totalPatients", patients.size());
            model.addAttribute("totalAppointments", doctorAppointments.size());
            model.addAttribute("totalReports", reportRepository.count());
        }
        
        return "doctor/dashboard";
    }
    
    @GetMapping("/patients")
    public String getPatients(Model model) {
        List<Patient> patients = patientRepository.findAll();
        model.addAttribute("patients", patients);
        return "doctor/patients";
    }
    
    @GetMapping("/patients/{id}")
    public String getPatientDetails(@PathVariable Long id, Model model) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient != null) {
            model.addAttribute("patient", patient);
            
            // Get patient's appointments
            List<Appointment> appointments = appointmentRepository.findAll();
            List<Appointment> patientAppointments = appointments.stream()
                    .filter(a -> a.getPatient().getId().equals(patient.getId()))
                    .toList();
            model.addAttribute("appointments", patientAppointments);
            
            // Get patient's reports
            List<Report> reports = reportRepository.findAll();
            model.addAttribute("reports", reports);
        }
        return "doctor/patient-details";
    }
    
    @GetMapping("/appointments")
    public String getAppointments(Model model) {
        // Get current doctor
        List<Doctor> allDoctors = doctorRepository.findAll();
        Doctor doctor = allDoctors.isEmpty() ? null : allDoctors.get(0);
        
        if (doctor != null) {
            List<Appointment> appointments = appointmentRepository.findAll();
            List<Appointment> doctorAppointments = appointments.stream()
                    .filter(a -> a.getDoctor().getId().equals(doctor.getId()))
                    .toList();
            model.addAttribute("appointments", doctorAppointments);
        }
        return "doctor/appointments";
    }
    
    @GetMapping("/reports")
    public String getReports(Model model) {
        List<Report> reports = reportRepository.findAll();
        model.addAttribute("reports", reports);
        return "doctor/reports";
    }
    
    @GetMapping("/prescriptions")
    public String getPrescriptions(Model model) {
        List<Prescription> prescriptions = prescriptionRepository.findAll();
        List<Patient> patients = patientRepository.findAll();
        List<Doctor> doctors = doctorRepository.findAll();
        
        model.addAttribute("prescriptions", prescriptions);
        model.addAttribute("patients", patients);
        model.addAttribute("doctors", doctors);
        return "doctor/prescriptions";
    }
    
    @PostMapping("/prescriptions/add")
    public String addPrescription(@RequestParam String medicineName,
                                  @RequestParam String dosage,
                                  @RequestParam String frequency,
                                  @RequestParam String duration,
                                  @RequestParam Long patientId,
                                  @RequestParam Long doctorId,
                                  @RequestParam String notes,
                                  Model model) {
        try {
            Patient patient = patientRepository.findById(patientId).orElse(null);
            Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
            
            if (patient != null && doctor != null) {
                Prescription prescription = new Prescription();
                prescription.setPatient(patient);
                prescription.setDoctor(doctor);
                prescription.setMedicineName(medicineName);
                prescription.setDosage(dosage);
                prescription.setFrequency(frequency);
                prescription.setDuration(duration);
                prescription.setPrescribedDate(LocalDate.now());
                prescription.setNotes(notes);
                
                prescriptionRepository.save(prescription);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "redirect:/doctor/prescriptions";
    }
}
