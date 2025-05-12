package com.yc.snackoverflow.constant;

/**
 * Error code constants for the application
 * Organized by category:
 * - General: 10000-19999
 * - Authentication: 20000-29999
 * - Product: 30000-39999
 * - Booking: 40000-49999
 */
public final class ErrorCode {
    // Success
    public static final int SUCCESS = 0;
    
    // General errors: 10000-19999
    public static final int SYSTEM_ERROR = 10000;
    public static final int PARAM_ERROR = 10001;
    public static final int RESOURCE_NOT_FOUND = 10002;
    public static final int DATA_ACCESS_ERROR = 10003;
    public static final int INVALID_REQUEST = 10004;
    public static final int OPERATION_FAILED = 10005;
    
    // Authentication errors: 20000-29999
    public static final int UNAUTHORIZED = 20000;
    public static final int TOKEN_EXPIRED = 20001;
    public static final int INVALID_CREDENTIALS = 20002;
    public static final int ACCESS_DENIED = 20003;
    public static final int FORBIDDEN = 20004;
    
    // Product errors: 30000-39999
    public static final int PRODUCT_NOT_FOUND = 30000;
    public static final int PRODUCT_CLASS_NOT_FOUND = 30001;
    public static final int PRODUCT_CREATE_FAILED = 30002;
    public static final int PRODUCT_UPDATE_FAILED = 30003;
    public static final int PRODUCT_DELETE_FAILED = 30004;
    
    // Booking errors: 40000-49999
    public static final int BOOKING_NOT_FOUND = 40000;
    public static final int BOOKING_CONFLICT = 40001;
    public static final int BOOKING_CREATE_FAILED = 40002;
    public static final int BOOKING_UPDATE_FAILED = 40003;
    public static final int BOOKING_DELETE_FAILED = 40004;
    
    // Member errors: 50000-59999
    public static final int MEMBER_NOT_FOUND = 50000;
    public static final int MEMBER_ALREADY_EXISTS = 50001;
    public static final int MEMBER_CREATE_FAILED = 50002;
    
    // Prevent instantiation
    private ErrorCode() {
        throw new AssertionError("Cannot instantiate constant class");
    }
}