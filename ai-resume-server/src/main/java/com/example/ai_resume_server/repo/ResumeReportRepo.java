package com.example.ai_resume_server.repo;

import com.example.ai_resume_server.models.ResumeReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeReportRepo extends JpaRepository<ResumeReport,Long> {
}
