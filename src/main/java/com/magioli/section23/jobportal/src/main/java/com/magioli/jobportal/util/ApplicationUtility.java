package com.magioli.jobportal.util;

import com.magioli.jobportal.constants.ApplicationConstants;
import com.magioli.jobportal.dto.JobApplicationDto;
import com.magioli.jobportal.dto.JobDto;
import com.magioli.jobportal.dto.ProfileDto;
import com.magioli.jobportal.entity.Job;
import com.magioli.jobportal.entity.JobApplication;
import com.magioli.jobportal.entity.JobPortalUser;
import com.magioli.jobportal.entity.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ApplicationUtility {

    public static String getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            return ApplicationConstants.SYSTEM;
        }
        Object principal = authentication.getPrincipal();
        String username;
        if (principal instanceof JobPortalUser jobPortalUser) {
            username = jobPortalUser.getEmail();
        } else {
            username = principal.toString();
        }
        return username;
    }

    public static JobDto transformJobToDto(Job job) {
        return new JobDto(
                job.getId(),
                job.getTitle(),
                job.getCompany().getId(),
                job.getCompany().getName(),
                job.getCompany().getLogo(),
                job.getLocation(),
                job.getWorkType(),
                job.getJobType(),
                job.getCategory(),
                job.getExperienceLevel(),
                job.getSalaryMin(),
                job.getSalaryMax(),
                job.getSalaryCurrency(),
                job.getSalaryPeriod(),
                job.getDescription(),
                job.getRequirements(),
                job.getBenefits(),
                job.getPostedDate(),
                job.getApplicationDeadline(),
                job.getApplicationsCount(),
                job.getFeatured(),
                job.getUrgent(),
                job.getRemote(),
                job.getStatus()
        );
    }

    public static JobApplicationDto mapToJobApplicationDto(JobApplication jobApplication) {
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

    public static ProfileDto mapToProfileDto(Profile profile, boolean includeBinaryData) {
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
