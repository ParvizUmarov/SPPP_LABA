package org.example.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.serviceInterface.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthAspect {

    private final AuthService authService;

    @Around("@annotation(org.example.aop.CheckAuth)")
    public Object checkAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("check auth method called: ");
        String token = joinPoint.getArgs()[0].toString();


        if (!authService.checkAuth(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You need to be authorized");
        }

        return joinPoint.proceed();
    }

}
