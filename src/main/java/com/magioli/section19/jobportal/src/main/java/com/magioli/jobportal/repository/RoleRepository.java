package com.magioli.jobportal.repository;

import com.magioli.jobportal.entity.Role;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {


    @Cacheable("roles")
    Optional<Role> findByName(String name);
}
