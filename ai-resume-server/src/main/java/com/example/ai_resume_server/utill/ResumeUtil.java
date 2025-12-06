package com.example.ai_resume_server.utill;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.ai_resume_server.DTO.ApiResponse;
import com.example.ai_resume_server.models.ResumeReport;
import com.example.ai_resume_server.repo.ResumeReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class ResumeUtil {
    @Autowired
    private ResumeReportRepo repo;

    public ResumeReport extractObj(String data) {



        String regex =
                "ATS_SCORE:\\s*([^;]+);\\s*" +
                        "STRENGTHS:\\s*([^;]+);\\s*" +
                        "IMPROVEMENTS:\\s*([^;]+);\\s*" +
                        "JOB_MATCH:\\s*([^;]+);\\s*" +
                        "MISSING_KEYWORDS:\\s*([^;]+);\\s*" +
                        "SUMMARY:\\s*(.*)";

        Matcher m = Pattern.compile(regex).matcher(data);
        ResumeReport response=new ResumeReport();
        if (!m.find()) {

            response.setError("Pattern mismatch");
            response.setMessage(data);
            return response;
        }

        response.setATS_SCORE(m.group(1).trim());
        response.setSTRENGTHS(m.group(2).trim());
        response.setIMPROVEMENTS(m.group(3).trim());
        response.setJOB_MATCH(m.group(4).trim());
        response.setMISSING_KEYWORDS(m.group(5).trim());
        response.setSUMMARY(m.group(6).trim());


        repo.save(response);
        return response;
    }

}
