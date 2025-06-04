package com.example.fortifyapp.controller;

import com.example.fortifyapp.entity.Vulnerability;
import com.example.fortifyapp.repository.VulnerabilityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/fpr")
public class FprController {

    private final VulnerabilityRepository vulnerabilityRepository;

    public FprController(VulnerabilityRepository vulnerabilityRepository) {
        this.vulnerabilityRepository = vulnerabilityRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFpr(@RequestParam("file") MultipartFile file) throws Exception {
        List<Vulnerability> list = parseFpr(file.getInputStream());
        vulnerabilityRepository.saveAll(list);
        return ResponseEntity.ok().build();
    }

    private List<Vulnerability> parseFpr(InputStream input) throws Exception {
        // Simple parsing example using DOM
        List<Vulnerability> list = new ArrayList<>();
        var doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
        var issues = doc.getElementsByTagName("Issue");
        for (int i = 0; i < issues.getLength(); i++) {
            var node = issues.item(i);
            var vuln = new Vulnerability();
            vuln.setCweId(node.getAttributes().getNamedItem("cweid").getNodeValue());
            vuln.setSeverity(node.getAttributes().getNamedItem("Friority").getNodeValue());
            vuln.setFilePath(node.getAttributes().getNamedItem("FileName").getNodeValue());
            list.add(vuln);
        }
        return list;
    }
}
