package com.magioli.jobportal.contact.service;

import com.magioli.jobportal.dto.ContactRequestDto;

public interface ContactService {

    boolean saveContact(ContactRequestDto contactRequestDto);
}
