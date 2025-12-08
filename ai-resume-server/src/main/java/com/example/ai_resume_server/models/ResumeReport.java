package com.example.ai_resume_server.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "ResumeReport")
public class ResumeReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeId;
    private String ATS_SCORE;
    private List<String> STRENGTHS;
    private String IMPROVEMENTS;
    private String JOB_MATCH;
    private List<String> JOB_RECOMMENDATION;
    private List<String> MISSING_KEYWORDS;
    private String SUMMARY;

    private String email;

    public void setMessage(String data) {
    }

    public void setError(String patternMismatch) {

    }
}

