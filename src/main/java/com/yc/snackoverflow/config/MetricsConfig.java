package com.yc.snackoverflow.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for application metrics
 */
@Configuration
public class MetricsConfig {

    /**
     * Register a default meter registry
     * 
     * @return Simple meter registry
     */
    @Bean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }

    /**
     * Enable @Timed annotation for measuring method execution time
     * 
     * @param registry Meter registry
     * @return TimedAspect for Spring AOP
     */
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}
