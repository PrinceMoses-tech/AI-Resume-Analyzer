package com.example.ai_resume_server.service;

import com.example.ai_resume_server.DTO.ApiResponse;
import com.example.ai_resume_server.DTO.Message;
import com.example.ai_resume_server.models.ResumeReport;
import com.example.ai_resume_server.repo.AccountRepo;
import com.example.ai_resume_server.repo.ResumeReportRepo;
import com.example.ai_resume_server.utill.ResumeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ResumeReportService {

    @Autowired
    private ResumeReportRepo repo;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private ResumeUtil resumeUtil;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${API_URL}")
    private String url;


    /**
     * Sends prompt to ML API and extracts ResumeReport.
     */
    public ResumeReport sendPrompt(Message prompt) {

        try {
            ApiResponse response = restTemplate.postForObject(url, prompt, ApiResponse.class);

            if (response == null || response.getResponse() == null) {
                return new ResumeReport(); // avoid NPE
            }
            ResumeReport report=resumeUtil.extractObj(response.getResponse());

            return report;

        } catch (Exception e) {
            e.printStackTrace();
            return new ResumeReport(); // fallback
        }
    }


    /**
     * Get cached reports for an email.
     * Cache key = email
     */
    @Cacheable(cacheNames = "resumeReport", key = "#email")
    public List<ResumeReport> getReport(String email) {

        Optional<List<ResumeReport>> optionalList = repo.findByEmailOrderByIdDesc(email);

        return optionalList.orElse(Collections.emptyList());
    }


    public boolean delete(Long id) {

        // Step 1: fetch report for email
        Optional<ResumeReport> optional = repo.findById(id);

        if (optional.isEmpty()) {
            return false;
        }

        String email = optional.get().getEmail();

        // Step 2: delete from DB
        repo.deleteById(id);

        // Step 3: clear cache manually
        if (cacheManager.getCache("resumeReport") != null) {
            cacheManager.getCache("resumeReport").evict(email);
        }

        return true;
    }

}
