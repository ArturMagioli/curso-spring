package com.magioli.jobportal.dto;

public record LoginResponseDto(String message, UserDto user, String jwtToken) {
}
