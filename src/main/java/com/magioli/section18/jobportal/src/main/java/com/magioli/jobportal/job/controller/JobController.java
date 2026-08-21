package com.magioli.jobportal.job.controller;

import com.magioli.jobportal.dto.JobDto;
import com.magioli.jobportal.job.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping(path = "/employer", version = "1.0")
    public ResponseEntity<List<JobDto>> getEmployerJobs(Authentication authentication) {
        String email = authentication.getPrincipal().toString();
        List<JobDto> jobDtos = jobService.getEmployerJobs(email);
        return ResponseEntity.ok(jobDtos);
    }

    @PostMapping(path = "/employer", version = "1.0")
    public ResponseEntity<JobDto> createJob(@RequestBody @Valid JobDto newJob, Authentication authentication) {
        String email = authentication.getPrincipal().toString();
        JobDto registeredJob = jobService.createJob(newJob, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredJob);
    }

    @PatchMapping(path = "/{jobId}/status/employer", version = "1.0")
    public ResponseEntity<?> updateJobStatus(@PathVariable String jobId,
                                                  @RequestBody Map<String, String> fields,
                                                  Authentication authentication) {
        String status = fields.get("status");
        if (status == null || status.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Status is required"));
        }
        String email = authentication.getPrincipal().toString();
        JobDto updatedJob = jobService.updateJobStatus(Long.valueOf(jobId), status.toUpperCase(), email);
        return ResponseEntity.ok(updatedJob);
    }
}
