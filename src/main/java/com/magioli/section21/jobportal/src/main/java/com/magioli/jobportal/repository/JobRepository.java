package com.magioli.jobportal.repository;

import com.magioli.jobportal.entity.Job;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    @CacheEvict(value = "jobs", allEntries = true)
    Job save(Job job);

}
