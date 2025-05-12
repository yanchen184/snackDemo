package com.yc.snackoverflow.exception;

import org.springframework.http.HttpStatus;

/**
 * @author meow
 */
public interface WebError {

    HttpStatus getStatusCode();

    String getErrorCode();

    String getErrorLog();

    WebException exception();

    WebException exception(Object... args);

    String getFormatAsString(Object... args);
}
