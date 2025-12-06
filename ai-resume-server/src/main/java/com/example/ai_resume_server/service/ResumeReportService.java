package com.example.ai_resume_server.service;

import com.example.ai_resume_server.DTO.ApiResponse;
import com.example.ai_resume_server.DTO.Message;
import com.example.ai_resume_server.models.ResumeReport;
import com.example.ai_resume_server.utill.ResumeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ResumeReportService {
    @Autowired
    private ResumeUtil resumeUtil;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${API_URL}")
    private  String url;
    public ResumeReport sendPrompt(Message prompt){

        try{
            ApiResponse response=restTemplate.postForObject(url,prompt,ApiResponse.class);
            assert response != null;
            return resumeUtil.extractObj(response.getResponse());

        }
        catch(Exception e){
            e.printStackTrace();
            return new ResumeReport();
        }
    }
}
