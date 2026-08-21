package com.magioli.jobportal.job.service;

import com.magioli.jobportal.dto.JobDto;

import java.util.List;

public interface JobService {

    List<JobDto> getEmployerJobs(String email);

    JobDto createJob(JobDto newJob, String email);

    JobDto updateJobStatus(Long jobId, String status, String email);
}
