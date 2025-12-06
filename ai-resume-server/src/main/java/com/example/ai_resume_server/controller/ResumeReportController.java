package com.example.ai_resume_server.controller;


import com.example.ai_resume_server.models.ResumeReport;
import com.example.ai_resume_server.repo.ResumeReportRepo;
import com.example.ai_resume_server.service.PDFExtractService;
import com.example.ai_resume_server.service.PDFValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/resume")
public class ResumeReportController {
    @Autowired
    private PDFValidatorService service;
    @Autowired
    private ResumeReportRepo repo;
    @Autowired
    private PDFExtractService pdfExtractService;
    @PostMapping("/upload/{email}")
    public ResponseEntity<?> uploadResume(@RequestParam("file")MultipartFile file,@RequestParam("description") String description,@PathVariable String email){

        String validate= service.validate(file);
        if(!validate.equals("VALID")) {
        return ResponseEntity.badRequest().body(Map.of("error",validate));
        }
        ResumeReport report=pdfExtractService.extractText(file,description);
        report.setEmail(email);
        repo.save(report);
        return ResponseEntity.ok(report);
    }
}
