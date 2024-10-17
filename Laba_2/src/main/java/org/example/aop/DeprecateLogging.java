package org.example.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DeprecateLogging {

    private static final Logger logger = LogManager.getLogger(DeprecateLogging.class);

    @After("@annotation(java.lang.Deprecated)")
    public void logDeprecatedMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.warn("Вызван метод, помеченный @Deprecated: {}", methodName);
    }

}
