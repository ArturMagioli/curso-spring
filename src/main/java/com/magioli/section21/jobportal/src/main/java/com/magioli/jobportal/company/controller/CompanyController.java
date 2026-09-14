package com.magioli.jobportal.company.controller;

import com.magioli.jobportal.dto.CompanyDto;
import com.magioli.jobportal.company.service.CompanyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping(path = "/public", version = "1.0")
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        List<CompanyDto> companyList = companyService.getAllCompanies();
        return ResponseEntity.ok(companyList);
    }

    @GetMapping(path = "/admin", version = "1.0")
    public ResponseEntity<List<CompanyDto>> getAllCompaniesForAdmin() {
        List<CompanyDto> companyDtoList = companyService.getAllCompaniesForAdmin();
        return ResponseEntity.ok(companyDtoList);
    }

    @PostMapping(path = "/admin", version = "1.0")
    public ResponseEntity<String> createCompany(@RequestBody @Valid CompanyDto companyDto) {
        boolean isCreated = companyService.createCompany(companyDto);
        if (isCreated) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Request processed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Request processing failed");
        }
    }

    @PutMapping(path = "/{id}/admin", version = "1.0")
    public ResponseEntity<String> updateCompanyDetails(@PathVariable @NotBlank String id,
        @RequestBody @Valid CompanyDto companyDto) {
        boolean isUpdated = companyService.updateCompanyDetails(Long.valueOf(id), companyDto);
        if (isUpdated) {
            return ResponseEntity.ok("Company details updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update Company details");
        }
    }

    @DeleteMapping(path = "/{id}/admin", version = "1.0")
    public ResponseEntity<String> deleteCompanyById(@PathVariable @NotBlank String id) {
        companyService.deleteCompanyById(Long.valueOf(id));
        return ResponseEntity.ok("Company record deleted successfully");
    }
}
