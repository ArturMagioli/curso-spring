package com.magioli.jobportal.repository;

import com.magioli.jobportal.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findContactsByStatus(String status);
}
