package com.example.employeemanagement.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.employeemanagement.controller..*(..))")
    public void logBeforeControllerMethod(JoinPoint joinPoint) {
        log.info("Calling controller method: {}", joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "execution(* com.example.employeemanagement.service..*(..))", returning = "result")
    public void logAfterServiceMethod(JoinPoint joinPoint, Object result) {
        log.info("Service method {} returned: {}", joinPoint.getSignature().toShortString(), result);
    }

    @Around("execution(* com.example.employeemanagement.controller..*(..))")
    public Object logControllerExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Controller method {} executed in {} ms", proceedingJoinPoint.getSignature().toShortString(), endTime - startTime);
        return result;
    }
}
