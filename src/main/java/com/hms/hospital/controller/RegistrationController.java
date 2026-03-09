package com.hms.hospital.controller;

import com.hms.hospital.entity.Patient;
import com.hms.hospital.entity.User;
import com.hms.hospital.repository.PatientRepository;
import com.hms.hospital.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerPatient(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String email,
                                  @RequestParam String name,
                                  @RequestParam String phone,
                                  @RequestParam String dateOfBirth,
                                  @RequestParam String address,
                                  @RequestParam String bloodGroup,
                                  @RequestParam(required = false) String medicalHistory) {
        // create user
        if (userRepository.findByUsername(username).isPresent()) {
            // username already exists, redirect back with error (could be improved)
            return "redirect:/register?error=username";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setRole("PATIENT");
        user.setEnabled(true);
        userRepository.save(user);

        // create patient
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

        return "redirect:/login?registered=true";
    }
}