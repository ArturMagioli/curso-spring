package com.magioli.jobportal.company.service;

import com.magioli.jobportal.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    List<CompanyDto> getAllCompanies();

    List<CompanyDto> getAllCompaniesForAdmin();

    boolean createCompany(CompanyDto companyDto);

    boolean updateCompanyDetails(Long id, CompanyDto companyDto);

    void deleteCompanyById(Long id);
}
