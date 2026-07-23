package com.magioli.jobportal.aspects;

import com.magioli.jobportal.dto.LoginResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoginSuccessAuditAspect {

    @AfterReturning(
            pointcut = "execution(* com.magioli.jobportal.auth.AuthController.apiLogin(..))",
            returning = "response"
    )
    public void logSuccessfullLogin(JoinPoint joinPoint, Object response) {

        if (!(response instanceof ResponseEntity<?> responseEntity)) {
            return;
        }
        Object body = responseEntity.getBody();
        if (!(body instanceof LoginResponseDto loginResponseDto)) {
            return;
        }

        if (loginResponseDto != null) {
            String username = loginResponseDto.user().getEmail();
            String role = loginResponseDto.user().getRole();
            log.info("LOGIN SUCCESS | User: {} | Role {}", username, role);
        }
    }
}
