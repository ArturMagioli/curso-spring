package com.magioli.jobportal.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.magioli.jobportal.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDto> findByEmail(String email);

    UserDto elevateUserRole(Long id);

    UserDto assignCompanyToEmployer(Long userId, Long companyId);

    ProfileDto createOrUpdateProfile(String userEmail, String profileJson,
                                     MultipartFile profilePicture, MultipartFile resume) throws JsonProcessingException;

    ProfileDto getProfile(String userEmail);

    ProfileDto getProfilePicture(String userEmail);

    ProfileDto getResume(String userEmail);

    JobDto saveJob(String userEmail, Long jobId);

    void unsaveJob(String userEmail, Long jobId);

    List<JobDto> getSavedJobs(String userEmail);

    JobApplicationDto applyJob(String userEmail, ApplyJobRequestDto applyJobRequestDto);

    void withdrawApplication(String userEmail, Long jobId);

    List<JobApplicationDto> getJobseekerApplications(String userEmail);
}
