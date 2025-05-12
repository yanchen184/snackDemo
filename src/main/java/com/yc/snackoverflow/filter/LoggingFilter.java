package com.yc.snackoverflow.filter;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.stream.Collectors;

@Slf4j
//@Component
@AllArgsConstructor
//@Order(1)
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        String method = request.getMethod();
        HttpServletRequest wrappedRequest = new HttpServletRequestWrapper(request) {
            private final String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

            @Override
            public BufferedReader getReader() {
                return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(body.getBytes())));
            }
        };
        Enumeration<String> parameterNames = request.getParameterNames();
        StringBuilder params = new StringBuilder();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            params.append(paramName).append("=").append(paramValue).append(", ");
        }

        if (!params.isEmpty()) {
            params.setLength(params.length() - 2);
        }

        String body = method.equalsIgnoreCase("POST") ? wrappedRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator())) : "";

        log.info("API: {}, method: {}, params: [{}], body: [{}]",
                path, method, params, body);

        filterChain.doFilter(request, response);
    }
}