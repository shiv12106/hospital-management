package com.hms.hospital.controller;

import com.hms.hospital.entity.Doctor;
import com.hms.hospital.entity.MedicalRecord;
import com.hms.hospital.entity.Patient;
import com.hms.hospital.service.MedicalRecordService;
import com.hms.hospital.service.PatientService;
import com.hms.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

// Temporarily disabled for testing
// @Controller
// @RequestMapping("/doctor")
public class MedicalRecordController {
    
    @Autowired
    private MedicalRecordService medicalRecordService;
    
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private DoctorService doctorService;
    
    @GetMapping("/medical-records")
    public String getMedicalRecords(Model model) {
        List<MedicalRecord> records = medicalRecordService.getAllMedicalRecords();
        model.addAttribute("records", records);
        return "doctor/medical-records";
    }
    
    @GetMapping("/medical-records/add")
    public String showAddMedicalRecordForm(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctor/medical-record-add";
    }
    
    @PostMapping("/medical-records/add")
    public String addMedicalRecord(@RequestParam String diagnosis,
                                 @RequestParam String treatment,
                                 @RequestParam String doctorNotes,
                                 @RequestParam String symptoms,
                                 @RequestParam String followUpInstructions,
                                 @RequestParam Long patientId,
                                 @RequestParam Long doctorId,
                                 Model model) {
        try {
            Patient patient = patientService.getPatientById(patientId).orElse(null);
            Doctor doctor = doctorService.getDoctorById(doctorId).orElse(null);
            
            if (patient != null && doctor != null) {
                MedicalRecord record = new MedicalRecord();
                record.setPatient(patient);
                record.setDoctor(doctor);
                record.setDiagnosis(diagnosis);
                record.setTreatment(treatment);
                record.setDoctorNotes(doctorNotes);
                record.setSymptoms(symptoms);
                record.setFollowUpInstructions(followUpInstructions);
                record.setRecordDate(LocalDate.now());
                
                medicalRecordService.saveMedicalRecord(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "redirect:/doctor/medical-records";
    }
    
    @GetMapping("/medical-records/patient/{patientId}")
    public String getPatientMedicalRecords(@PathVariable Long patientId, Model model) {
        Patient patient = patientService.getPatientById(patientId).orElse(null);
        if (patient != null) {
            List<MedicalRecord> records = medicalRecordService.getMedicalRecordsByPatient(patient);
            model.addAttribute("patient", patient);
            model.addAttribute("records", records);
        }
        return "doctor/patient-medical-records";
    }
}
