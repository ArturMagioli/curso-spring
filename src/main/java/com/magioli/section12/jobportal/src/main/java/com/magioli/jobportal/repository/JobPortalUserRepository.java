package com.magioli.jobportal.repository;

import com.magioli.jobportal.entity.JobPortalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobPortalUserRepository extends JpaRepository<JobPortalUser, Long> {

    Optional<JobPortalUser> readByEmailOrMobileNumber(String email, String mobileNumber);

}
