package com.magioli.jobportal.user.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.magioli.jobportal.constants.ApplicationConstants;
import com.magioli.jobportal.dto.*;
import com.magioli.jobportal.entity.*;
import com.magioli.jobportal.repository.*;
import com.magioli.jobportal.user.service.UserService;
import com.magioli.jobportal.util.ApplicationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final CompanyRepository companyRepository;
    private final JobPortalUserRepository jobPortalUserRepository;
    private final RoleRepository roleRepository;
    private final ProfileRepository profileRepository;
    private final JobRepository jobRepository;
    private final JobApplicationRepository jobApplicationRepository;

    @Override
    public Optional<UserDto> findByEmail(String email) {
       Optional<UserDto> userDto = Optional.empty();
       Optional<JobPortalUser> jobPortalUser = jobPortalUserRepository.findByEmail(email);
       if (jobPortalUser.isPresent()) {
           userDto = Optional.of(convertUserToDto(jobPortalUser.get()));
       }
       return userDto;
    }

    @Transactional
    @Override
    public UserDto elevateUserRole(Long id) {
        JobPortalUser jobPortalUser = jobPortalUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no such user with id: " + id));
        if (ApplicationConstants.ROLE_EMPLOYER.equals(jobPortalUser.getRole().getName())) {
            return convertUserToDto(jobPortalUser);
        }
        if (ApplicationConstants.ROLE_ADMIN.equals(jobPortalUser.getRole().getName())) {
            throw new RuntimeException("Cannot change user admin role to employer role");
        }
        Role employerRole = roleRepository.findByName(ApplicationConstants.ROLE_EMPLOYER).get();
        jobPortalUser.setRole(employerRole);
        return convertUserToDto(jobPortalUser);
    }

    @Transactional
    @Override
    public UserDto assignCompanyToEmployer(Long userId, Long companyId) {
        JobPortalUser jobPortalUser = jobPortalUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("There is no such user with id: " + userId));
        if (!ApplicationConstants.ROLE_EMPLOYER.equals(jobPortalUser.getRole().getName())) {
            throw new RuntimeException("User must be an employer to be assigned to a company");
        }
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("There is no such company with id: " + companyId));
        jobPortalUser.setCompany(company);
        return convertUserToDto(jobPortalUser);
    }

    private UserDto convertUserToDto(JobPortalUser jobPortalUser) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(jobPortalUser, userDto);
        userDto.setUserId(jobPortalUser.getId());
        Company userCompany = jobPortalUser.getCompany();
        if (userCompany != null) {
            userDto.setCompanyId(userCompany.getId());
            userDto.setCompanyName(userCompany.getName());
        }
        userDto.setRole(jobPortalUser.getRole().getName());
        return userDto;
    }

    @Transactional
    @Override
    public ProfileDto createOrUpdateProfile(String userEmail, String profileJson, MultipartFile profilePicture, MultipartFile resume) throws JsonProcessingException {
        JobPortalUser user = jobPortalUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        Profile profile = user.getProfile();
        if (null == profile) {
            profile = new Profile();
            profile.setUser(user);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ProfileDto profileDto = objectMapper.readValue(profileJson, ProfileDto.class);
        Profile savedProfile = profileRepository.save(mapToProfile(profile, profileDto, profilePicture, resume));
        return mapToProfileDto(savedProfile, false);
    }

    @Override
    public ProfileDto getProfile(String userEmail) {
        JobPortalUser user = jobPortalUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        if (user.getProfile() == null) {
            return null;
        }
        return mapToProfileDto(user.getProfile(), false);
    }

    @Override
    public ProfileDto getProfilePicture(String userEmail) {
        JobPortalUser user = jobPortalUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        if (user.getProfile() == null) {
            return null;
        }
        return mapToProfileDto(user.getProfile(), true);
    }

    @Override
    public ProfileDto getResume(String userEmail) {
        JobPortalUser user = jobPortalUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        if (user.getProfile() == null) {
            return null;
        }
        return mapToProfileDto(user.getProfile(), true);
    }

    @Transactional
    @Override
    public JobDto saveJob(String userEmail, Long jobId) {
        JobPortalUser user = jobPortalUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));
        user.getSavedJobs().add(job);
        return ApplicationUtility.transformJobToDto(job);
    }

    @Transactional
    @Override
    public void unsaveJob(String userEmail, Long jobId) {
        JobPortalUser user = jobPortalUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));
        user.getSavedJobs().remove(job);
    }

    @Override
    public List<JobDto> getSavedJobs(String userEmail) {
        JobPortalUser user = jobPortalUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        return user.getSavedJobs().stream()
                .map(job -> ApplicationUtility.transformJobToDto(job))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public JobApplicationDto applyJob(String userEmail, ApplyJobRequestDto applyJobRequestDto) {
        JobPortalUser user = jobPortalUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        Long jobId = applyJobRequestDto.jobId();
        if (jobApplicationRepository.existsByUserIdAndJobId(user.getId(), jobId)) {
            throw new RuntimeException("You have already applied for this job");
        }
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));
        JobApplication jobApplication = new JobApplication();
        jobApplication.setUser(user);
        jobApplication.setJob(job);
        jobApplication.setAppliedAt(Instant.now());
        jobApplication.setStatus(ApplicationConstants.PENDING);
        jobApplication.setCoverLetter(applyJobRequestDto.coverLetter());
        JobApplication saved = jobApplicationRepository.save(jobApplication);
        job.setApplicationsCount(job.getApplicationsCount() != null ? job.getApplicationsCount() + 1 : 1);
        return mapToJobApplicationDto(saved);
    }

    @Transactional
    @Override
    public void withdrawApplication(String userEmail, Long jobId) {
        JobPortalUser user = jobPortalUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        if (!jobApplicationRepository.existsByUserIdAndJobId(user.getId(), jobId)) {
            throw new RuntimeException("You not applied for this job");
        }
        jobApplicationRepository.deleteByUserIdAndJobId(user.getId(), jobId);
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + jobId));
        if (job.getApplicationsCount() != null && job.getApplicationsCount() > 0) {
            job.setApplicationsCount(job.getApplicationsCount() - 1);
        }
    }

    @Override
    public List<JobApplicationDto> getJobseekerApplications(String userEmail) {
        JobPortalUser user = jobPortalUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        return user.getJobApplications().stream()
                .map(this::mapToJobApplicationDto)
                .collect(Collectors.toList());
    }

    private JobApplicationDto mapToJobApplicationDto(JobApplication jobApplication) {
        Profile profile = jobApplication.getUser().getProfile();
        ProfileDto profileDto = null;
        if (profile != null) {
            profileDto = mapToProfileDto(profile, true);
        }
        return new JobApplicationDto(
                jobApplication.getId(),
                jobApplication.getUser().getId(),
                jobApplication.getUser().getName(),
                jobApplication.getUser().getEmail(),
                jobApplication.getUser().getMobileNumber(),
                profileDto,
                ApplicationUtility.transformJobToDto(jobApplication.getJob()),
                jobApplication.getAppliedAt(),
                jobApplication.getStatus(),
                jobApplication.getCoverLetter(),
                jobApplication.getNotes()
        );
    }

    private Profile mapToProfile(Profile profile, ProfileDto profileDto,
                                 MultipartFile profilePicture, MultipartFile resume) {
        profile.setJobTitle(profileDto.jobTitle());
        profile.setLocation(profileDto.location());
        profile.setExperienceLevel(profileDto.experienceLevel());
        profile.setProfessionalBio(profileDto.professionalBio());
        profile.setPortfolioWebsite(profileDto.portfolioWebsite());

        if (profilePicture != null && !profilePicture.isEmpty()) {
            try {
                profile.setProfilePicture(profilePicture.getBytes());
                profile.setProfilePictureName(profilePicture.getOriginalFilename());
                profile.setProfilePictureType(profilePicture.getContentType());
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload profile picture", e);
            }
        }

        if (resume != null && !resume.isEmpty()) {
            try {
                profile.setResume(resume.getBytes());
                profile.setResumeName(resume.getOriginalFilename());
                profile.setResumeType(resume.getContentType());
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload resume", e);
            }
        }
        return profile;
    }

    private ProfileDto mapToProfileDto(Profile profile, boolean includeBinaryData) {
        ProfileDto dto;
        if (includeBinaryData) {
            dto = new ProfileDto(profile.getId(), profile.getUser().getId(),
                    profile.getJobTitle(), profile.getLocation(), profile.getExperienceLevel(),
                    profile.getProfessionalBio(), profile.getPortfolioWebsite(), profile.getProfilePicture(),
                    profile.getProfilePictureName(), profile.getProfilePictureType(), profile.getResume(),
                    profile.getResumeName(), profile.getResumeType(), profile.getCreatedAt(), profile.getUpdatedAt()
            );
        } else {
            dto = new ProfileDto(profile.getId(), profile.getUser().getId(),
                    profile.getJobTitle(), profile.getLocation(), profile.getExperienceLevel(),
                    profile.getProfessionalBio(), profile.getPortfolioWebsite(), null,
                    profile.getProfilePictureName(), profile.getProfilePictureType(), null,
                    profile.getResumeName(), profile.getResumeType(), profile.getCreatedAt(), profile.getUpdatedAt());
        }
        return dto;
    }
}
