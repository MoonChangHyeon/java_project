package com.example.fortifyapp.repository;

import com.example.fortifyapp.entity.Scan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScanRepository extends JpaRepository<Scan, Long> {
}
