package com.magioli.jobportal.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.magioli.jobportal.dto.JobDto;
import com.magioli.jobportal.dto.ProfileDto;
import com.magioli.jobportal.dto.UserDto;
import com.magioli.jobportal.user.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/search/admin", version = "1.0")
    public ResponseEntity<?> searchUserByEmail(@RequestParam @NotBlank @Email String email) {
        Optional<UserDto> userDto = userService.findByEmail(email);
        if (userDto.isEmpty()) {
            return ResponseEntity.
                    status(HttpStatus.BAD_REQUEST)
                    .body("There is no user with email: " + email);
        }

        return ResponseEntity.ok(userDto.get());
    }

    @PatchMapping(path = "/{id}/role/employer/admin", version = "1.0")
    public ResponseEntity<UserDto> elevateUserRole(@PathVariable Long id) {
        UserDto updatedUser = userService.elevateUserRole(id);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping(path = "/{userId}/company/{companyId}/admin")
    public ResponseEntity<UserDto> assignCompanyToEmployer(@PathVariable Long userId,
                                                       @PathVariable Long companyId) {
        UserDto updatedUser = userService.assignCompanyToEmployer(userId, companyId);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping(path = "/profile/jobseeker", version = "1.0",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileDto> createOrUpdateProfile(
            @RequestPart(value = "profile") String profileJson,
            @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture,
            @RequestPart(value = "resume", required = false) MultipartFile resume,
            Authentication authentication) throws JsonProcessingException {
        String userEmail = authentication.getPrincipal().toString();
        ProfileDto savedProfile = userService.createOrUpdateProfile(
                userEmail, profileJson, profilePicture, resume);
        return ResponseEntity.ok(savedProfile);
    }

    @GetMapping(path = "/profile/jobseeker", version = "1.0")
    public ResponseEntity<ProfileDto> getProfile(Authentication authentication) {
        String userEmail = authentication.getPrincipal().toString();
        ProfileDto profileDto = userService.getProfile(userEmail);
        return ResponseEntity.ok(profileDto);
    }

    @GetMapping(path = "/profile/picture/jobseeker", version = "1.0")
    public ResponseEntity<byte[]> getProfilePicture(Authentication authentication) {
        String userEmail = authentication.getPrincipal().toString();
        ProfileDto profileDto = userService.getProfilePicture(userEmail);
        byte[] picture = profileDto.profilePicture();
        if (picture == null || picture.length == 0) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(profileDto.profilePictureType()));
        headers.setContentLength(picture.length);
        return new ResponseEntity<>(picture, headers, HttpStatus.OK);
    }

    @GetMapping(path = "/profile/resume/jobseeker", version = "1.0")
    public ResponseEntity<byte[]> getResume(Authentication authentication) {
        String userEmail = authentication.getPrincipal().toString();
        ProfileDto profileDto = userService.getResume(userEmail);
        byte[] resume = profileDto.resume();
        if (resume == null || resume.length == 0) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(profileDto.resumeType()));
        headers.setContentLength(resume.length);
        headers.setContentDispositionFormData("attachment", profileDto.resumeName());
        return new ResponseEntity<>(resume, headers, HttpStatus.OK);
    }

    @PostMapping(path = "/saved-jobs/{jobId}/jobseeker", version = "1.0")
    public ResponseEntity<JobDto> saveJob(@PathVariable Long jobId,
                                          Authentication authentication) {
        String userEmail = authentication.getPrincipal().toString();
        JobDto savedJob = userService.saveJob(userEmail, jobId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJob);
    }

    @DeleteMapping(path = "/saved-jobs/{jobId}/jobseeker", version = "1.0")
    public ResponseEntity<String> unsaveJob(@PathVariable Long jobId,
                                            Authentication authentication) {
        String userEmail = authentication.getPrincipal().toString();
        userService.unsaveJob(userEmail, jobId);
        return ResponseEntity.ok("Job unsaved successfully");
    }

    @GetMapping(path = "/saved-jobs/jobseeker", version = "1.0")
    public ResponseEntity<List<JobDto>> getSavedJobs(Authentication authentication) {
        String userEmail = authentication.getPrincipal().toString();
        List<JobDto> savedJobs = userService.getSavedJobs(userEmail);
        return ResponseEntity.ok(savedJobs);
    }
}
