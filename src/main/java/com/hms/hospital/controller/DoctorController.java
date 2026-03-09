package com.hms.hospital.controller;

import com.hms.hospital.entity.Department;
import com.hms.hospital.entity.Doctor;
import com.hms.hospital.repository.DepartmentRepository;
import com.hms.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public String getDoctors(Model model) {
        List<Doctor> doctors = doctorRepository.findAll();
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("doctors", doctors);
        model.addAttribute("departments", departments);
        return "doctors/list";
    }

    @PostMapping
    public String addDoctor(@RequestParam String name,
                            @RequestParam String specialization,
                            @RequestParam String email,
                            @RequestParam String phone,
                            @RequestParam Long departmentId,
                            @RequestParam(required = false) String qualifications,
                            @RequestParam(required = false) Integer experience) {
        Department dept = departmentId != null ? departmentRepository.findById(departmentId).orElse(null) : null;
        Doctor doc = new Doctor();
        doc.setName(name);
        doc.setSpecialization(specialization);
        doc.setEmail(email);
        doc.setPhone(phone);
        doc.setDepartment(dept);
        doc.setQualifications(qualifications);
        doc.setExperience(experience);
        doctorRepository.save(doc);
        return "redirect:/admin/doctors";
    }
}
