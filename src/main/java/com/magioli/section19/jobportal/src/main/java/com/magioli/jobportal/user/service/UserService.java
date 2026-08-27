package com.magioli.jobportal.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.magioli.jobportal.dto.JobDto;
import com.magioli.jobportal.dto.ProfileDto;
import com.magioli.jobportal.dto.UserDto;
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
}
