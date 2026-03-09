package com.hms.hospital.controller;

import com.hms.hospital.entity.Patient;
import com.hms.hospital.entity.User;
import com.hms.hospital.entity.Appointment;
import com.hms.hospital.entity.Prescription;
import com.hms.hospital.repository.PatientRepository;
import com.hms.hospital.repository.UserRepository;
import com.hms.hospital.repository.DoctorRepository;
import com.hms.hospital.repository.DepartmentRepository;
import com.hms.hospital.repository.AppointmentRepository;
import com.hms.hospital.service.PatientService;
import com.hms.hospital.service.PrescriptionService;
import com.hms.hospital.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserDashboardController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private PrescriptionService prescriptionService;
    
    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username).orElse(null);
            model.addAttribute("user", user);
            if (user != null) {
                model.addAttribute("patients", patientRepository.findByUser(user));
            }
        }
        return "user/profile";
    }
    
    @GetMapping("/medical-records")
    public String medicalRecords(Model model, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            
            // Patient data security: Only show records for the logged-in patient
            Patient currentPatient = patientService.getCurrentPatient(username).orElse(null);
            if (currentPatient != null) {
                model.addAttribute("patient", currentPatient);
                model.addAttribute("message", "Medical records functionality will be available soon.");
            }
        }
        return "user/medical-records";
    }

    @GetMapping("/prescriptions")
    public String prescriptions(Model model, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            
            // Patient data security: Only show prescriptions for the logged-in patient
            Patient currentPatient = patientService.getCurrentPatient(username).orElse(null);
            if (currentPatient != null) {
                List<Prescription> prescriptions = prescriptionService.getPrescriptionsByPatientOrderByDate(currentPatient);
                model.addAttribute("prescriptions", prescriptions);
                model.addAttribute("patient", currentPatient);
            }
        }
        return "user/prescriptions";
    }
    
    @GetMapping("/appointments")
    public String appointments(Model model, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            
            // Patient data security: Only show appointments for the logged-in patient
            List<com.hms.hospital.entity.Appointment> appointments = appointmentService.getCurrentUserAppointments(username);
            model.addAttribute("appointments", appointments);
            
            // Get current patient for display
            appointmentService.getCurrentPatient(username).ifPresent(patient -> {
                model.addAttribute("patient", patient);
            });
        }
        return "user/appointments";
    }
    
    @GetMapping("/doctors")
    public String doctors(Model model, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            
            // Get current patient's doctors through appointments
            List<com.hms.hospital.entity.Appointment> appointments = appointmentService.getCurrentUserAppointments(username);
            
            // Extract unique doctors from appointments
            java.util.Set<com.hms.hospital.entity.Doctor> uniqueDoctors = appointments.stream()
                .map(appointment -> appointment.getDoctor())
                .filter(doctor -> doctor != null)
                .collect(java.util.stream.Collectors.toSet());
            
            model.addAttribute("doctors", uniqueDoctors);
            
            // Get current patient for display
            appointmentService.getCurrentPatient(username).ifPresent(patient -> {
                model.addAttribute("patient", patient);
            });
        }
        return "user/doctors";
    }
    
    @GetMapping("/alerts")
    public String alerts(Model model, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            
            // Get current patient for display
            appointmentService.getCurrentPatient(username).ifPresent(patient -> {
                model.addAttribute("patient", patient);
            });
            
            // TODO: In a real application, you would fetch actual alerts from database
            // For now, the template has static alerts
        }
        return "user/alerts";
    }
    
    @GetMapping("/prescription/{id}/download")
    public ResponseEntity<String> downloadPrescription(@PathVariable Long id, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            Patient currentPatient = patientService.getCurrentPatient(username).orElse(null);
            
            if (currentPatient != null) {
                // Security check: Only allow download of own prescriptions
                List<Prescription> patientPrescriptions = prescriptionService.getPrescriptionsByPatient(currentPatient);
                
                for (Prescription prescription : patientPrescriptions) {
                    if (prescription.getId().equals(id)) {
                        // Generate simple text-based prescription
                        String prescriptionText = generatePrescriptionText(prescription);
                        
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.TEXT_PLAIN);
                        headers.setContentDispositionFormData("attachment", "prescription_" + id + ".txt");
                        
                        return ResponseEntity.ok()
                                .headers(headers)
                                .body(prescriptionText);
                    }
                }
            }
        }
        
        return ResponseEntity.notFound().build();
    }
    
    private String generatePrescriptionText(Prescription prescription) {
        StringBuilder sb = new StringBuilder();
        sb.append("PRESCRIPTION\n");
        sb.append("============\n\n");
        sb.append("Patient: ").append(prescription.getPatient().getName()).append("\n");
        sb.append("Doctor: ").append(prescription.getDoctor().getName()).append("\n");
        sb.append("Date: ").append(prescription.getPrescribedDate()).append("\n\n");
        sb.append("MEDICINE: ").append(prescription.getMedicineName()).append("\n");
        sb.append("DOSAGE: ").append(prescription.getDosage()).append("\n");
        sb.append("FREQUENCY: ").append(prescription.getFrequency()).append("\n");
        sb.append("DURATION: ").append(prescription.getDuration()).append("\n");
        if (prescription.getNotes() != null && !prescription.getNotes().isEmpty()) {
            sb.append("NOTES: ").append(prescription.getNotes()).append("\n");
        }
        sb.append("\n============\n");
        sb.append("Hospital Management System\n");
        
        return sb.toString();
    }

    @GetMapping("/bills")
    public String bills(Model model, Authentication authentication) {
        return "user/bills";
    }
    
    @GetMapping("/appointments/add")
    public String addAppointmentForm(Model model, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            
            // Get current patient
            appointmentService.getCurrentPatient(username).ifPresent(patient -> {
                model.addAttribute("patients", List.of(patient)); // Only show current patient
            });
            
            // Get all doctors and departments for selection
            model.addAttribute("doctors", doctorRepository.findAll());
            model.addAttribute("departments", departmentRepository.findAll());
        }
        return "user/appointments_add";
    }
    
    @PostMapping("/appointments/add")
    public String addAppointment(@RequestParam Long patientId,
                                @RequestParam Long doctorId,
                                @RequestParam(required = false) Long departmentId,
                                @RequestParam String dateTime,
                                @RequestParam String reason,
                                Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            
            // Security check: Only allow creating appointments for current user
            appointmentService.getCurrentPatient(username).ifPresent(patient -> {
                if (patient.getId().equals(patientId)) {
                    Appointment appointment = new Appointment();
                    appointment.setPatient(patient);
                    appointment.setDoctor(doctorId != null ? doctorRepository.findById(doctorId).orElse(null) : null);
                    appointment.setDepartment(departmentId != null ? departmentRepository.findById(departmentId).orElse(null) : null);
                    appointment.setAppointmentDate(LocalDateTime.parse(dateTime));
                    appointment.setReason(reason);
                    appointment.setStatus("SCHEDULED");
                    appointmentRepository.save(appointment);
                }
            });
        }
        return "redirect:/user/appointments";
    }
    
    @GetMapping("/appointments/{id}")
    public String viewAppointment(@PathVariable Long id, Model model, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            
            // Security check: Only allow viewing own appointments
            appointmentService.getCurrentPatient(username).ifPresent(patient -> {
                appointmentRepository.findById(id).ifPresent(appointment -> {
                    if (appointment.getPatient().getId().equals(patient.getId()) && 
                        !appointment.getStatus().equals("COMPLETED")) {
                        appointment.setStatus("CANCELLED");
                        appointmentRepository.save(appointment);
                    }
                });
            });
        }
        return "redirect:/user/appointments";
    }
}
