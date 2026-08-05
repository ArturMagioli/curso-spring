package com.magioli.jobportal.contact.service;

import com.magioli.jobportal.dto.ContactRequestDto;
import com.magioli.jobportal.dto.ContactResponseDto;

import java.util.List;

public interface ContactService {

    boolean saveContact(ContactRequestDto contactRequestDto);

    List<ContactResponseDto> fetchNewContactMsgs();
}
