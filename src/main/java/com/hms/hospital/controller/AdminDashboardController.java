package com.hms.hospital.controller;

import com.hms.hospital.entity.Patient;
import com.hms.hospital.entity.User;
import com.hms.hospital.entity.Appointment;
import com.hms.hospital.entity.MedicalRecord;
import com.hms.hospital.repository.PatientRepository;
import com.hms.hospital.repository.UserRepository;
import com.hms.hospital.repository.AppointmentRepository;
import com.hms.hospital.repository.DoctorRepository;
import com.hms.hospital.repository.DepartmentRepository;
import com.hms.hospital.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }
    
    @GetMapping("/patients")
    public String patients(Model model) {
        List<Patient> patients = patientRepository.findAll();
        List<User> users = userRepository.findAll();
        model.addAttribute("patients", patients);
        model.addAttribute("users", users);
        return "patients/list";
    }
    
    @PostMapping("/patients")
    public String addPatient(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam String dateOfBirth,
                           @RequestParam String address,
                           @RequestParam String bloodGroup,
                           @RequestParam(required = false) String medicalHistory,
                           @RequestParam Long userId) {
        // Find user by ID
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "redirect:/admin/patients?error=user";
        }
        
        // Create patient
        Patient patient = new Patient();
        patient.setName(name);
        patient.setEmail(email);
        patient.setPhone(phone);
        patient.setDateOfBirth(LocalDate.parse(dateOfBirth));
        patient.setAddress(address);
        patient.setBloodGroup(bloodGroup);
        patient.setMedicalHistory(medicalHistory);
        patient.setUser(user);
        patientRepository.save(patient);
        
        return "redirect:/admin/patients";
    }
    
    @GetMapping("/appointments")
    public String appointments(Model model) {
        List<Appointment> appointments = appointmentRepository.findAll();
        model.addAttribute("appointments", appointments);
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
        return "appointments/list";
    }
    
    @PostMapping("/appointments")
    public String addAppointment(@RequestParam Long patientId,
                           @RequestParam Long doctorId,
                           @RequestParam(required = false) Long departmentId,
                           @RequestParam String dateTime,
                           @RequestParam String reason) {
        Appointment appointment = new Appointment();
        appointment.setPatient(patientRepository.findById(patientId).orElse(null));
        appointment.setDoctor(doctorRepository.findById(doctorId).orElse(null));
        appointment.setDepartment(departmentId != null ? departmentRepository.findById(departmentId).orElse(null) : null);
        appointment.setAppointmentDate(java.time.LocalDateTime.parse(dateTime));
        appointment.setReason(reason);
        appointment.setStatus("SCHEDULED");
        appointmentRepository.save(appointment);
        
        return "redirect:/admin/appointments";
    }
    
    @GetMapping("/appointments/edit/{id}")
    public String editAppointmentForm(@PathVariable Long id, Model model) {
        appointmentRepository.findById(id).ifPresent(appointment -> {
            model.addAttribute("appointment", appointment);
        });
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
        return "appointments/edit";
    }
    
    @PostMapping("/appointments/edit/{id}")
    public String updateAppointment(@PathVariable Long id,
                               @RequestParam Long patientId,
                               @RequestParam Long doctorId,
                               @RequestParam(required = false) Long departmentId,
                               @RequestParam String dateTime,
                               @RequestParam String reason) {
        appointmentRepository.findById(id).ifPresent(appointment -> {
            appointment.setPatient(patientRepository.findById(patientId).orElse(null));
            appointment.setDoctor(doctorRepository.findById(doctorId).orElse(null));
            appointment.setDepartment(departmentId != null ? departmentRepository.findById(departmentId).orElse(null) : null);
            appointment.setAppointmentDate(java.time.LocalDateTime.parse(dateTime));
            appointment.setReason(reason);
            appointmentRepository.save(appointment);
        });
        
        return "redirect:/admin/appointments";
    }
    
    @GetMapping("/appointments/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentRepository.deleteById(id);
        return "redirect:/admin/appointments";
    }
    
    @GetMapping("/medical-records")
    public String medicalRecords(Model model) {
        model.addAttribute("medicalRecords", medicalRecordRepository.findAll());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());
        return "medical-records/list";
    }
}
