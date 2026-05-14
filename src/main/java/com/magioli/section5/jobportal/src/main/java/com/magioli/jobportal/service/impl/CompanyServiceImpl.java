package com.magioli.jobportal.service.impl;

import com.magioli.jobportal.entity.Company;
import com.magioli.jobportal.repository.CompanyRepository;
import com.magioli.jobportal.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
