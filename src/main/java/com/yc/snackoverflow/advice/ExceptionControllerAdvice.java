package com.yc.snackoverflow.advice;

import com.yc.snackoverflow.exception.WebError;
import com.yc.snackoverflow.exception.WebException;
import com.yc.snackoverflow.handler.ResultData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler(WebException.class)
    public ResultData<Object> handleWebException(final WebException e) {
        final WebError webError = e.getWebError();
//        final String resMessage = messageSource.getMessage(webError.getErrorCode(), e.getArgs(), LocaleContextHolder.getLocale());
//        String resMessage;
//        if (e.getArgs() != null && e.getArgs().length > 0) {
//            resMessage = MessageFormat.format(webError.getErrorLog(), e.getArgs());
//        } else {
//            resMessage = webError.getErrorLog();
//        }
        log.error("[WebException]: {} ", e.getWebErrorMsg());
        return ResultData.fail(webError.getErrorCode(), e.getWebErrorMsg());
    }

}
