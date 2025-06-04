package com.example.fortifyapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Scan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projectName;
    private LocalDateTime startedAt;
    private String status;
    private String fprFile;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Vulnerability> vulnerabilities;
}
