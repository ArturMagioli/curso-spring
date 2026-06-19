package com.magioli.jobportal.dto;

import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

public record ErrorResponseDto(String apiPath, HttpStatusCode errorCode, String errorMessage,
                               LocalDateTime errorTime) {
}
