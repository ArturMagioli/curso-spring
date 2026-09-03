package com.magioli.jobportal.job.service.impl;

import com.magioli.jobportal.constants.ApplicationConstants;
import com.magioli.jobportal.dto.JobDto;
import com.magioli.jobportal.entity.Company;
import com.magioli.jobportal.entity.Job;
import com.magioli.jobportal.entity.JobPortalUser;
import com.magioli.jobportal.job.service.JobService;
import com.magioli.jobportal.repository.JobPortalUserRepository;
import com.magioli.jobportal.repository.JobRepository;
import com.magioli.jobportal.util.ApplicationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobPortalUserRepository jobPortalUserRepository;
    private final JobRepository jobRepository;

    @Cacheable("jobs")
    @Override
    public List<JobDto> getEmployerJobs(String email) {
        JobPortalUser employer = jobPortalUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employer not found"));
        if (employer.getCompany() == null) {
            throw new RuntimeException("Employer does not have any company assigned to him");
        }
        Company employerCompany = employer.getCompany();
        List<JobDto> jobDtos = employerCompany.getJobs()
                .stream()
                .map(job -> ApplicationUtility.transformJobToDto(job))
                .collect(Collectors.toList());
        return jobDtos;
    }

    @Transactional
    @Override
    public JobDto createJob(JobDto newJob, String email) {
        JobPortalUser employer = jobPortalUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employer not found"));
        if (employer.getCompany() == null) {
            throw new RuntimeException("The employer does not have any assigned company");
        }
        Job createdJob = convertToJob(newJob);
        createdJob.setPostedDate(Instant.now());
        createdJob.setApplicationsCount(0);
        createdJob.setStatus(ApplicationConstants.DRAFT_STATUS);
        createdJob.setCompany(employer.getCompany());
        Job savedJob = jobRepository.save(createdJob);
        return ApplicationUtility.transformJobToDto(savedJob);
    }

    @Transactional
    @Override
    public JobDto updateJobStatus(Long jobId, String status, String email) {
        JobPortalUser employer = jobPortalUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employer not found"));
        if (employer.getCompany() == null) {
            throw new RuntimeException("The employer does not have any assigned company");
        }
        if (!status.equals(ApplicationConstants.DRAFT_STATUS)
                && !status.equals(ApplicationConstants.ACTIVE_STATUS)
                && !status.equals(ApplicationConstants.CLOSED_STATUS)) {
            throw new RuntimeException("The status must be DRAFT, ACTIVE or CLOSED");
        }

        Company employerCompany = employer.getCompany();
        Job updatedJob = employerCompany.getJobs()
                .stream()
                .filter(job -> job.getId().equals(jobId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no job available from this company with this id"));
        updatedJob.setStatus(status);
        return ApplicationUtility.transformJobToDto(updatedJob);
    }

    private Job convertToJob(JobDto jobDto) {
        Job newJob = new Job();
        BeanUtils.copyProperties(jobDto, newJob);
        return newJob;
    }
}
