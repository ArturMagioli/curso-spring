package com.magioli.jobportal.user.service;

import com.magioli.jobportal.dto.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> findByEmail(String email);

    UserDto elevateUserRole(Long id);

    UserDto assignCompanyToEmployer(Long userId, Long companyId);
}
