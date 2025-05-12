package com.yc.snackoverflow.config;

import com.yc.snackoverflow.config.web.converter.IntegerEnumConverterFactory;
import com.yc.snackoverflow.config.web.converter.StringToEnumConverterFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.lang.reflect.Field;

/**
 * JPA configuration with support for auditing and custom enum converters
 * 
 * @author meow
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {

    @EventListener(ApplicationReadyEvent.class)
    public void config() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> aClass = Class.forName("org.springframework.data.projection.ProxyProjectionFactory");
        Field field = aClass.getDeclaredField("CONVERSION_SERVICE");
        field.setAccessible(true);
        GenericConversionService service = (GenericConversionService) field.get(null);
        service.addConverterFactory(new StringToEnumConverterFactory());
        service.addConverterFactory(new IntegerEnumConverterFactory());
    }
}