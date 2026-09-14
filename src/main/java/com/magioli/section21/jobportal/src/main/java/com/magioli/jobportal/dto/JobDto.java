package com.magioli.jobportal.dto;

import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public record JobDto(
        Long id,

        @NotBlank(message = "Title field should not be empty")
        @Size(min = 5, max = 255, message = "Title should be less than 255 characters")
        String title,

        Long companyId,

        String companyName,

        String companyLogo,

        @NotBlank(message = "Location field should not be empty")
        String location,

        @NotBlank(message = "Work type field should not be empty")
        String workType,

        @NotBlank(message = "Job type field should not be empty")
        String jobType,

        @NotBlank(message = "Category field should not be empty")
        String category,

        @NotBlank(message = "Experience level field should not be empty")
        String experienceLevel,


        @NotNull(message = "Minimum salary is required")
        @DecimalMin(value = "0.0", message = "Minimum salary must be positive")
        BigDecimal salaryMin,

        @NotNull(message = "Maximum salary is required")
        @DecimalMin(value = "0.0", message = "Maximum salary must be positive")
        BigDecimal salaryMax,

        @NotBlank(message = "Salary currency field should not be empty")
        String salaryCurrency,

        @NotBlank(message = "Salary period field should not be empty")
        String salaryPeriod,

        @NotBlank(message = "Description field should not be empty")
        String description,

        String requirements,
        String benefits,
        Instant postedDate,

        Instant applicationDeadline,
        Integer applicationsCount,
        Boolean featured,
        Boolean urgent,
        Boolean remote,
        String status
) implements Serializable {
}
