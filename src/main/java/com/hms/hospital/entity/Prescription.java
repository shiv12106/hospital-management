package com.hms.hospital.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "prescriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    
    @Column(nullable = false)
    private String medicineName;
    
    private String dosage; // e.g., "500mg"
    
    private String frequency; // e.g., "Three times a day"
    
    private String duration; // e.g., "10 days"
    
    @Column(nullable = false)
    private LocalDate prescribedDate;
    
    private String notes;
}
