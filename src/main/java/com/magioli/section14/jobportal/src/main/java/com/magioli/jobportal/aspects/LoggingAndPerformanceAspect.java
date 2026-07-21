package com.magioli.jobportal.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAndPerformanceAspect {

    @Around("execution(* com.magioli.jobportal..*.*(..))")
    public Object logAndMeasureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();
        Object[] methodArgs = joinPoint.getArgs();
        log.debug("Entering method: {}", methodName);
        log.debug("Arguments: {}", methodArgs);
        // Proceed with actual business method
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;
        log.debug("Method executed successfully: {}", methodName);
        log.debug("Execution time: {} ms", executionTime);
        return result;
    }
}
