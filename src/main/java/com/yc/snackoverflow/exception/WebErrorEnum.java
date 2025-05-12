package com.yc.snackoverflow.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


/**
 * @author meow
 */
@Getter
@RequiredArgsConstructor
public enum WebErrorEnum implements WebError {

    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ERR0001", "Custom internal Error. " ),
    MISSING_TOKEN(HttpStatus.UNAUTHORIZED, "ERR0002", "Missing token. "),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "ERR0003", "Invalid token. "),
    INVALID_ARG(HttpStatus.BAD_REQUEST, "ERR0004", "Invalid arg: [%s]. "),
    UPSERT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "ERR0005", "Upsert failed. "),
    BOOKING_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR0006", "Booking not found. Booking id : [%s]. "),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR0007", "Member not found: [%s]. "),
    MEMBER_EXISTS(HttpStatus.CONFLICT, "ERR0008", "Member exists. "),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR0009", "Product not found. "),
    PRODUCT_EXISTS(HttpStatus.CONFLICT, "ERR0010", "Product exists. "),
    PRODUCT_CLASS_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR0011", "Product class not found. "),
    METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "ERR0012", "Method argument not valid. "),
    METHOD_ARGUMENT_ERROR(HttpStatus.BAD_REQUEST, "ERR0013", "Method argument error. "),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ERR0014", "Internal server error. "),
    ASSESS_DENIED_ERROR(HttpStatus.FORBIDDEN, "ERR0015", "Access denied. "),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "ERR0016", "JWT expired. "),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "ERR0017", "User already exists. "),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "ERR0018", "Email already exists. "),
    PRODUCT_CLASS_ALREADY_EXISTS(HttpStatus.CONFLICT, "ERR0019", "Product class already exists. "),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "ERR0020", "Unauthorized access. "),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR0021", "User not found. "),
    ;

    private final HttpStatus statusCode;
    private final String errorCode;
    private final String errorLog;

    @Override
    public WebException exception() {
        return new WebException(this);
    }

    @Override
    public WebException exception(Object... args) {
        return new WebException(this, args);
    }

    public String getFormatAsString(Object... args) {
        return String.format(errorLog, args);
    }
}
