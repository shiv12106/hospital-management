package com.hms.hospital.config;

import com.hms.hospital.entity.*;
import com.hms.hospital.repository.*;
import com.hms.hospital.repository.MedicalRecordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UserRepository userRepository, 
                        DepartmentRepository departmentRepo,
                        DoctorRepository doctorRepo,
                        PatientRepository patientRepo,
                        AppointmentRepository appointmentRepo,
                        PrescriptionRepository prescriptionRepo,
                        MedicalRecordRepository medicalRecordRepo,
                        PasswordEncoder passwordEncoder) {
        return args -> {

            // Create users first
            if(userRepository.findByUsername("admin").isEmpty()){
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");
                admin.setEmail("admin@hospital.com");
                admin.setEnabled(true);
                userRepository.save(admin);
                System.out.println("Created admin user: admin");
            }

            if(userRepository.findByUsername("doctor").isEmpty()){
                User doctor = new User();
                doctor.setUsername("doctor");
                doctor.setPassword(passwordEncoder.encode("doctor123"));
                doctor.setRole("DOCTOR");
                doctor.setEmail("doctor@hospital.com");
                doctor.setEnabled(true);
                userRepository.save(doctor);
                System.out.println("Created doctor user: doctor");
            }

            if(userRepository.findByUsername("patient").isEmpty()){
                User patient = new User();
                patient.setUsername("patient");
                patient.setPassword(passwordEncoder.encode("patient123"));
                patient.setRole("PATIENT");
                patient.setEmail("patient@hospital.com");
                patient.setEnabled(true);
                userRepository.save(patient);
                System.out.println("Created patient user: patient");
            }

            // Create departments
            Department cardiology = new Department(null, "Cardiology", "Heart and cardiovascular diseases", "555-0101");
            Department orthopedics = new Department(null, "Orthopedics", "Bones and joints", "555-0102");
            Department neurology = new Department(null, "Neurology", "Brain and nervous system", "555-0103");
            Department general = new Department(null, "General Medicine", "General healthcare", "555-0104");
            Department pediatrics = new Department(null, "Pediatrics", "Child healthcare", "555-0105");
            
            departmentRepo.save(cardiology);
            departmentRepo.save(orthopedics);
            departmentRepo.save(neurology);
            departmentRepo.save(general);
            departmentRepo.save(pediatrics);

            // Create doctors with Marathi/Maharashtrian style names
            User drUser1 = new User();
            drUser1.setUsername("dr.rajesh");
            drUser1.setPassword(passwordEncoder.encode("doctor123"));
            drUser1.setRole("DOCTOR");
            drUser1.setEmail("rajesh.patel@hospital.com");
            drUser1.setEnabled(true);
            userRepository.save(drUser1);

            Doctor dr1 = new Doctor(null, "डॉ. राजेश पाटील", "Cardiologist", "rajesh.patel@hospital.com", "555-1001", cardiology, "MD, DM", 15);
            doctorRepo.save(dr1);

            User drUser2 = new User();
            drUser2.setUsername("dr.sunita");
            drUser2.setPassword(passwordEncoder.encode("doctor123"));
            drUser2.setRole("DOCTOR");
            drUser2.setEmail("sunita.deshmukh@hospital.com");
            drUser2.setEnabled(true);
            userRepository.save(drUser2);

            Doctor dr2 = new Doctor(null, "डॉ. सुनीता देशमुख", "Orthopedic Surgeon", "sunita.deshmukh@hospital.com", "555-1002", orthopedics, "MS, MCh", 12);
            doctorRepo.save(dr2);

            User drUser3 = new User();
            drUser3.setUsername("dr.vijay");
            drUser3.setPassword(passwordEncoder.encode("doctor123"));
            drUser3.setRole("DOCTOR");
            drUser3.setEmail("vijay.kumar@hospital.com");
            drUser3.setEnabled(true);
            userRepository.save(drUser3);

            Doctor dr3 = new Doctor(null, "डॉ. विजय कुमार", "Neurologist", "vijay.kumar@hospital.com", "555-1003", neurology, "MD, DM", 18);
            doctorRepo.save(dr3);

            User drUser4 = new User();
            drUser4.setUsername("dr.anjali");
            drUser4.setPassword(passwordEncoder.encode("doctor123"));
            drUser4.setRole("DOCTOR");
            drUser4.setEmail("anjali.sharma@hospital.com");
            drUser4.setEnabled(true);
            userRepository.save(drUser4);

            Doctor dr4 = new Doctor(null, "डॉ. अंजली शर्मा", "General Physician", "anjali.sharma@hospital.com", "555-1004", general, "MD", 8);
            doctorRepo.save(dr4);

            User drUser5 = new User();
            drUser5.setUsername("dr.ashok");
            drUser5.setPassword(passwordEncoder.encode("doctor123"));
            drUser5.setRole("DOCTOR");
            drUser5.setEmail("ashok.jadhav@hospital.com");
            drUser5.setEnabled(true);
                userRepository.save(drUser5);

            Doctor dr5 = new Doctor(null, "डॉ. अशोक जाधव", "Pediatrician", "ashok.jadhav@hospital.com", "555-1005", pediatrics, "MD, DCH", 10);
            doctorRepo.save(dr5);

            // Create patients with Marathi/Maharashtrian style names
            User patUser1 = new User();
            patUser1.setUsername("suresh.patil");
            patUser1.setPassword(passwordEncoder.encode("patient123"));
            patUser1.setRole("PATIENT");
            patUser1.setEmail("suresh.patil@email.com");
            patUser1.setEnabled(true);
            userRepository.save(patUser1);

            Patient patient1 = new Patient(null, "सुरेश पाटील", "suresh.patil@email.com", "555-2001", LocalDate.of(1985, 6, 15), "पुणे, महाराष्ट्र", "O+", "Diabetes", drUser1);
            patientRepo.save(patient1);

            User patUser2 = new User();
            patUser2.setUsername("priya.sharma");
            patUser2.setPassword(passwordEncoder.encode("patient123"));
            patUser2.setRole("PATIENT");
            patUser2.setEmail("priya.sharma@email.com");
            patUser2.setEnabled(true);
            userRepository.save(patUser2);

            Patient patient2 = new Patient(null, "प्रिया शर्मा", "priya.sharma@email.com", "555-2002", LocalDate.of(1990, 8, 22), "नागपूर, महाराष्ट्र", "A+", "Hypertension", drUser2);
            patientRepo.save(patient2);

            User patUser3 = new User();
            patUser3.setUsername("rahul.deshmukh");
            patUser3.setPassword(passwordEncoder.encode("patient123"));
            patUser3.setRole("PATIENT");
            patUser3.setEmail("rahul.deshmukh@email.com");
            patUser3.setEnabled(true);
            userRepository.save(patUser3);

            Patient patient3 = new Patient(null, "राहुल देशमुख", "rahul.deshmukh@email.com", "555-2003", LocalDate.of(1988, 12, 10), "मुंबई, महाराष्ट्र", "B+", "Asthma", drUser3);
            patientRepo.save(patient3);

            User patUser4 = new User();
            patUser4.setUsername("anita.kulkarni");
            patUser4.setPassword(passwordEncoder.encode("patient123"));
            patUser4.setRole("PATIENT");
            patUser4.setEmail("anita.kulkarni@email.com");
            patUser4.setEnabled(true);
            userRepository.save(patUser4);

            Patient patient4 = new Patient(null, "अनिता कुळकर्णी", "anita.kulkarni@email.com", "555-2004", LocalDate.of(1992, 3, 25), "पुणे, महाराष्ट्र", "AB+", "None", drUser4);
            patientRepo.save(patient4);

            User patUser5 = new User();
            patUser5.setUsername("vijay.thorat");
            patUser5.setPassword(passwordEncoder.encode("patient123"));
            patUser5.setRole("PATIENT");
            patUser5.setEmail("vijay.thorat@email.com");
            patUser5.setEnabled(true);
            userRepository.save(patUser5);

            Patient patient5 = new Patient(null, "विजय थोरात", "vijay.thorat@email.com", "555-2005", LocalDate.of(1987, 9, 8), "नाशिक, महाराष्ट्र", "O+", "None", drUser5);
            patientRepo.save(patient5);

            User patUser6 = new User();
            patUser6.setUsername("meena.jadhav");
            patUser6.setPassword(passwordEncoder.encode("patient123"));
            patUser6.setRole("PATIENT");
            patUser6.setEmail("meena.jadhav@email.com");
            patUser6.setEnabled(true);
            userRepository.save(patUser6);

            Patient patient6 = new Patient(null, "मीना जाधव", "meena.jadhav@email.com", "555-2006", LocalDate.of(1995, 5, 18), "कोल्हापूर, महाराष्ट्र", "A+", "None", drUser5);
            patientRepo.save(patient6);

            // Create some sample appointments
            Appointment apt1 = new Appointment(null, patient1, dr1, cardiology, LocalDateTime.now().plusDays(5), "Regular checkup", "SCHEDULED", null);
            Appointment apt2 = new Appointment(null, patient2, dr2, orthopedics, LocalDateTime.now().plusDays(3), "Knee pain consultation", "SCHEDULED", null);
            Appointment apt3 = new Appointment(null, patient3, dr3, neurology, LocalDateTime.now().plusDays(7), "Headache evaluation", "SCHEDULED", null);
            Appointment apt4 = new Appointment(null, patient4, dr4, general, LocalDateTime.now().plusDays(2), "General health checkup", "SCHEDULED", null);
            Appointment apt5 = new Appointment(null, patient5, dr5, pediatrics, LocalDateTime.now().plusDays(10), "Child vaccination", "SCHEDULED", null);
            
            appointmentRepo.save(apt1);
            appointmentRepo.save(apt2);
            appointmentRepo.save(apt3);
            appointmentRepo.save(apt4);
            appointmentRepo.save(apt5);

            // Create some sample prescriptions
            Prescription pres1 = new Prescription(null, patient1, dr1, "Amlodipine", "5mg", "Once daily", "90 days", LocalDate.now(), "Take with water after meals");
            Prescription pres2 = new Prescription(null, patient2, dr2, "Ibuprofen", "400mg", "Three times daily", "7 days", LocalDate.now().minusDays(5), "Take with food");
            Prescription pres3 = new Prescription(null, patient3, dr3, "Paracetamol", "500mg", "As needed for fever", "30 days", LocalDate.now().minusDays(10), "Maximum 4 tablets per day");
            
            prescriptionRepo.save(pres1);
            prescriptionRepo.save(pres2);
            prescriptionRepo.save(pres3);

            // Create some sample medical records
            MedicalRecord record1 = new MedicalRecord();
            record1.setPatient(patient1);
            record1.setDoctor(dr1);
            record1.setDiagnosis("Hypertension Stage 1");
            record1.setTreatment("Prescribed Amlodipine 5mg daily. Lifestyle modifications including low-salt diet and regular exercise recommended.");
            record1.setFollowUpInstructions("Blood pressure monitoring weekly");
            record1.setRecordDate(LocalDate.now().minusDays(15));
            record1.setSymptoms("Occasional headaches, fatigue");
            
            MedicalRecord record2 = new MedicalRecord();
            record2.setPatient(patient2);
            record2.setDoctor(dr2);
            record2.setDiagnosis("Osteoarthritis of right knee");
            record2.setTreatment("Prescribed Ibuprofen for pain management. Physical therapy recommended. Consider orthopedic consultation for further evaluation.");
            record2.setFollowUpInstructions("Follow up in 4 weeks");
            record2.setRecordDate(LocalDate.now().minusDays(10));
            record2.setSymptoms("Morning stiffness, pain on movement");
            
            MedicalRecord record3 = new MedicalRecord();
            record3.setPatient(patient3);
            record3.setDoctor(dr3);
            record3.setDiagnosis("Tension headache");
            record3.setTreatment("Prescribed Paracetamol as needed. Stress management techniques recommended. Adequate hydration and sleep advised.");
            record3.setFollowUpInstructions("Return if symptoms worsen");
            record3.setRecordDate(LocalDate.now().minusDays(5));
            record3.setSymptoms("Bilateral head pain, stress-related");
            
            MedicalRecord record4 = new MedicalRecord();
            record4.setPatient(patient4);
            record4.setDoctor(dr4);
            record4.setDiagnosis("Type 2 Diabetes");
            record4.setTreatment("Started on metformin. Dietary counseling provided. Regular blood glucose monitoring instructed.");
            record4.setFollowUpInstructions("HbA1c check in 3 months");
            record4.setRecordDate(LocalDate.now().minusDays(20));
            record4.setSymptoms("Increased thirst, frequent urination");
            
            MedicalRecord record5 = new MedicalRecord();
            record5.setPatient(patient5);
            record5.setDoctor(dr5);
            record5.setDiagnosis("Upper respiratory infection");
            record5.setTreatment("Prescribed antibiotics. Symptomatic treatment advised. Adequate rest and hydration recommended.");
            record5.setFollowUpInstructions("Follow up if fever persists beyond 3 days");
            record5.setRecordDate(LocalDate.now().minusDays(7));
            record5.setSymptoms("Fever, sore throat, cough");
            
            medicalRecordRepo.save(record1);
            medicalRecordRepo.save(record2);
            medicalRecordRepo.save(record3);
            medicalRecordRepo.save(record4);
            medicalRecordRepo.save(record5);

            System.out.println("Sample data with Marathi/Maharashtrian names created successfully!");
        };
    }
}
