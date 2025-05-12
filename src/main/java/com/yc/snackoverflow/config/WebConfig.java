package com.yc.snackoverflow.config;

import com.yc.snackoverflow.config.web.converter.IntegerEnumConverterFactory;
import com.yc.snackoverflow.config.web.converter.StringToEnumConverterFactory;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.Locale;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Bean
    public LocaleResolver localeResolver() {
        final AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setSupportedLocales(Arrays.asList(Locale.TAIWAN, Locale.US));
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }

    @Override
    protected void addFormatters(FormatterRegistry registry) {
        ApplicationConversionService.configure(registry);
        registry.addConverterFactory(new StringToEnumConverterFactory());
        registry.addConverterFactory(new IntegerEnumConverterFactory());
    }
}
