package com.example.fortifyapp.controller;

import com.example.fortifyapp.entity.Scan;
import com.example.fortifyapp.service.FortifyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/scan")
public class ScanController {

    private final FortifyService fortifyService;

    public ScanController(FortifyService fortifyService) {
        this.fortifyService = fortifyService;
    }

    @PostMapping
    public ResponseEntity<Scan> runScan(@RequestParam String project,
                                        @RequestParam("source") MultipartFile source) throws IOException, InterruptedException {
        Path tempDir = Files.createTempDirectory("sca-src");
        Path srcFile = tempDir.resolve(source.getOriginalFilename());
        Files.copy(source.getInputStream(), srcFile);
        Scan scan = fortifyService.runScan(project, srcFile.toString());
        return ResponseEntity.ok(scan);
    }

    @GetMapping
    public List<Scan> listScans() {
        return fortifyService.listScans();
    }
}
