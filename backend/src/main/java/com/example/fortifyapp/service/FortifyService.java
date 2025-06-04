package com.example.fortifyapp.service;

import com.example.fortifyapp.entity.Scan;
import com.example.fortifyapp.repository.ScanRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FortifyService {

    private final ScanRepository scanRepository;

    public FortifyService(ScanRepository scanRepository) {
        this.scanRepository = scanRepository;
    }

    public Scan runScan(String project, String sourcePath) throws IOException, InterruptedException {
        Scan scan = new Scan();
        scan.setProjectName(project);
        scan.setStartedAt(LocalDateTime.now());
        scan.setStatus("RUNNING");
        scanRepository.save(scan);

        ProcessBuilder pb = new ProcessBuilder("sourceanalyzer", "-b", project, "-scan", "-f", project + ".fpr", sourcePath);
        Process process = pb.start();
        int exit = process.waitFor();
        scan.setStatus(exit == 0 ? "DONE" : "FAILED");
        scan.setFprFile(project + ".fpr");
        return scanRepository.save(scan);
    }

    public List<Scan> listScans() {
        return scanRepository.findAll();
    }
}
