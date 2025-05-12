package com.yc.snackoverflow.aspect;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Aspect for measuring and logging method execution time
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class TimingAspect {

    private final MeterRegistry meterRegistry;

    /**
     * Measure execution time of all service methods
     * 
     * @param joinPoint Join point for advice
     * @return Result of the method execution
     * @throws Throwable If the method execution throws an exception
     */
    @Around("execution(* com.yc.snackoverflow.service.*.*(..))")
    public Object measureServiceMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        return measureExecutionTime(joinPoint, "service");
    }

    /**
     * Measure execution time of all controller methods
     * 
     * @param joinPoint Join point for advice
     * @return Result of the method execution
     * @throws Throwable If the method execution throws an exception
     */
    @Around("execution(* com.yc.snackoverflow.controller.*.*(..))")
    public Object measureControllerMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        return measureExecutionTime(joinPoint, "controller");
    }

    /**
     * Measure execution time of all repository methods
     * 
     * @param joinPoint Join point for advice
     * @return Result of the method execution
     * @throws Throwable If the method execution throws an exception
     */
    @Around("execution(* com.yc.snackoverflow.repository.*.*(..))")
    public Object measureRepositoryMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        return measureExecutionTime(joinPoint, "repository");
    }

    /**
     * Measure execution time of a method
     * 
     * @param joinPoint Join point for advice
     * @param type Type of component (service, controller, repository)
     * @return Result of the method execution
     * @throws Throwable If the method execution throws an exception
     */
    private Object measureExecutionTime(ProceedingJoinPoint joinPoint, String type) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        
        Timer.Sample sample = Timer.start(meterRegistry);
        
        try {
            return joinPoint.proceed();
        } finally {
            long executionTime = sample.stop(Timer.builder("app.method.execution.time")
                    .tag("type", type)
                    .tag("class", className)
                    .tag("method", methodName)
                    .description("Method execution time")
                    .register(meterRegistry));
            
            // Log execution time for slow methods (> 100ms)
            if (executionTime > TimeUnit.MILLISECONDS.toNanos(100)) {
                log.warn("{} method {}.{} took {}ms to execute", 
                        type, className, methodName, 
                        TimeUnit.NANOSECONDS.toMillis(executionTime));
            }
        }
    }
}
