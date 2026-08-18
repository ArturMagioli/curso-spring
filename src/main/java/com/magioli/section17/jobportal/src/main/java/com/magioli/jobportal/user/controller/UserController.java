package com.magioli.jobportal.user.controller;

import com.magioli.jobportal.dto.UserDto;
import com.magioli.jobportal.user.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

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
}
