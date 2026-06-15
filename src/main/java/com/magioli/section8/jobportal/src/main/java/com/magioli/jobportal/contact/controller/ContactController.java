package com.magioli.jobportal.contact.controller;

import com.magioli.jobportal.contact.service.ContactService;
import com.magioli.jobportal.dto.ContactRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping(version = "1.0")
    public ResponseEntity<String> saveContactMsg(@RequestBody @Valid ContactRequestDto contactRequestDto) {
        boolean isSaved = contactService.saveContact(contactRequestDto);
        if (isSaved) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Request processed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Request processing failed");
        }
    }

    @GetMapping
    public ResponseEntity<String> fetchOpenContacts(
            @RequestParam
            @Validated
            @NotBlank(message = "Status is required")
            @Size(min = 4, message = "Status param should be min of 4 chars")
            String status
    ) {
        return ResponseEntity.ok("There are the contacts with the given status: " + status);
    }
}
