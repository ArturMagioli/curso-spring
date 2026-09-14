package com.magioli.jobportal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record ContactRequestDto(
        @NotBlank(message = "Email field is required")
        @Email(message = "Invalid email address")
        String email,

        @NotBlank(message = "message field is required")
        @Size(min = 5, max = 500, message = "Message must be between 5 and 500 characters")
        String message,

        @NotBlank(message = "Name field is required")
        @Size(min = 5, max = 30, message = "Name must be between 5 and 30 characters")
        String name,

        @NotBlank(message = "Subject field is required")
        @Size(min = 5, max = 150, message = "Subject must be between 5 and 150 characters")
        String subject,

        @NotBlank(message = "UserType field is required")
        @Pattern(regexp = "Job Seeker|Employer|Other", message = "UserType must be one of: Job Seeker, Employer, Other")
        String userType
) implements Serializable {
}
