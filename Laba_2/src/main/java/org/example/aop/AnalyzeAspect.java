package org.example.aop;


import lombok.Data;
import lombok.Getter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.bot.LoggerConsole;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class AnalyzeAspect {
    @Getter
    private final Map<String, Long> memo = new ConcurrentHashMap<>();

    private final LoggerConsole logger;

    public AnalyzeAspect(LoggerConsole logger) {
        this.logger = logger;
    }

    @Around("@within(org.example.aop.Analyze) || @annotation(org.example.aop.Analyze)")
    public Object analyze(ProceedingJoinPoint jp) throws Throwable {
        var key = jp.getSignature().toString();
        var start = System.nanoTime();
        try {
            return jp.proceed();
        }
        finally {
            long duration = System.nanoTime() - start;
            memo.put(key, duration);
        }
    }


    @Data
    public static class Metrics {
        private long totalNano;

        public void update(long timeElapsedNano) {
            totalNano += timeElapsedNano;
        }

        @Override
        public String toString() {
            return "Metrics{" +
                    "totalNano=" + totalNano + '}';
        }
    }

}
