package com.hms.hospital.controller;

import com.hms.hospital.entity.Report;
import com.hms.hospital.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin/reports")
public class ReportController {

    @Autowired
    private ReportRepository reportRepository;

    @GetMapping
    public String getReports(Model model) {
        List<Report> reports = reportRepository.findAll();
        model.addAttribute("reports", reports);
        return "reports/list";
    }

    @PostMapping
    public String addReport(@RequestParam String title,
                            @RequestParam String content) {
        Report r = new Report();
        r.setTitle(title);
        r.setContent(content);
        r.setCreatedDate(LocalDate.now());
        reportRepository.save(r);
        return "redirect:/admin/reports";
    }
}
