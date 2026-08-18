package com.magioli.jobportal.user.service.impl;

import com.magioli.jobportal.constants.ApplicationConstants;
import com.magioli.jobportal.dto.UserDto;
import com.magioli.jobportal.entity.Company;
import com.magioli.jobportal.entity.JobPortalUser;
import com.magioli.jobportal.entity.Role;
import com.magioli.jobportal.repository.CompanyRepository;
import com.magioli.jobportal.repository.JobPortalUserRepository;
import com.magioli.jobportal.repository.RoleRepository;
import com.magioli.jobportal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final CompanyRepository companyRepository;
    private final JobPortalUserRepository jobPortalUserRepository;
    private final RoleRepository roleRepository;

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
}
