package com.hms.hospital.controller;

import com.hms.hospital.entity.Department;
import com.hms.hospital.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public String getDepartments(Model model) {
        List<Department> deps = departmentRepository.findAll();
        model.addAttribute("departments", deps);
        return "departments/list";
    }

    @PostMapping
    public String addDepartment(@RequestParam String name,
                                @RequestParam(required = false) String description,
                                @RequestParam(required = false) String phone) {
        Department d = new Department();
        d.setName(name);
        d.setDescription(description);
        d.setPhone(phone);
        departmentRepository.save(d);
        return "redirect:/admin/departments";
    }
}
