package com.yc.snackoverflow.handler;


import com.yc.snackoverflow.exception.WebErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.stream.Collectors;

@Slf4j
//@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final String INVALID_ARGUMENTS_PREFIX = "Invalid Arguments : ";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String resultMessage = handleArgumentRelatedExceptionMessage(ex);

        log.error(resultMessage);

        return new ResponseEntity<>(ResultData.fail(WebErrorEnum.METHOD_ARGUMENT_NOT_VALID.getErrorCode(), resultMessage), WebErrorEnum.METHOD_ARGUMENT_NOT_VALID.getStatusCode());
    }


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String resultMessage = handleArgumentRelatedExceptionMessage(ex);
        log.error(ex.getMessage());
        return new ResponseEntity<>(ResultData.fail(WebErrorEnum.METHOD_ARGUMENT_ERROR.getErrorCode(), resultMessage), WebErrorEnum.METHOD_ARGUMENT_ERROR.getStatusCode());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String resultMessage = handleArgumentRelatedExceptionMessage(ex);
        log.error(ex.getMessage());
        return new ResponseEntity<>(ResultData.fail(WebErrorEnum.METHOD_ARGUMENT_ERROR.getErrorCode(), resultMessage), WebErrorEnum.METHOD_ARGUMENT_ERROR.getStatusCode());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleSQLException(DataAccessException ex) {
        String resultMessage = extractSQLErrorMessage(ex);
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(ResultData.fail(WebErrorEnum.INTERNAL_SERVER_ERROR.getErrorCode(), resultMessage));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultData<String> handleGenericException(final Exception ex) {
        log.error("GenericException : ", ex);
        return ResultData.fail(WebErrorEnum.INTERNAL_ERROR.getErrorCode(), WebErrorEnum.INTERNAL_ERROR.getErrorLog());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResultData<String> handleConstraintViolationException(final ConstraintViolationException ex) {

        String resultMessage = handleArgumentRelatedExceptionMessage(ex);

        log.error(resultMessage);

        return ResultData.fail(WebErrorEnum.METHOD_ARGUMENT_ERROR.getErrorCode(), resultMessage);
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResultData<String> handleAccessDeniedException(final Exception ex) {

        log.error("GenericException : ", ex);
        return ResultData.fail(WebErrorEnum.ASSESS_DENIED_ERROR.getErrorCode(), WebErrorEnum.ASSESS_DENIED_ERROR.getErrorLog());
    }


    private String handleErrors(BindException bindException) {
        return bindException.getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    return fieldName + " -> " + message + "  ";
                })
                .collect(Collectors.joining(","));
    }

//    private String handleConstraintViolations(ConstraintViolationException ex) {
//        return ex
//                .getConstraintViolations().stream()
//                .map(error -> {
//                    String fieldName = StreamSupport.stream(error.getPropertyPath().spliterator(), false).reduce((first, second) -> second).orElse(null).toString();
//                    String message = error.getMessage();
//                    return fieldName + " -> " + message + "  ";
//                })
//                .collect(Collectors.joining(","));
//    }

    private String handleArgumentRelatedExceptionMessage(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException) {
            return INVALID_ARGUMENTS_PREFIX.concat(handleErrors((MethodArgumentNotValidException) ex));
//        } else if (ex instanceof ConstraintViolationException) {
//            return INVALID_ARGUMENTS_PREFIX.concat(handleConstraintViolations((ConstraintViolationException) ex));
        } else if (ex instanceof BindException) {
            return INVALID_ARGUMENTS_PREFIX.concat(handleErrors((BindException) ex));
        } else if (ex instanceof MissingServletRequestParameterException) {
            return INVALID_ARGUMENTS_PREFIX.concat(((MissingServletRequestParameterException) ex).getParameterName());
        } else if (ex instanceof HttpMessageNotReadableException) {
            return INVALID_ARGUMENTS_PREFIX.concat(ex.getLocalizedMessage());
        }
        return INVALID_ARGUMENTS_PREFIX;
    }

    private String extractSQLErrorMessage(DataAccessException ex) {
        Throwable rootCause = ex.getRootCause();
        if (rootCause == null) {
            return "Unknown cause";
        }
        if (rootCause instanceof java.sql.SQLException) {
            String sqlState = ((java.sql.SQLException) rootCause).getSQLState();
            String errorMessage = rootCause.getMessage();
            return "SQLState: " + sqlState + ", ErrorMessage: " + errorMessage;
        } else {
            return "Unknown SQLState";
        }
    }
}
