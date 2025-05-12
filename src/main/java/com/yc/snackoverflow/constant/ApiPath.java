package com.yc.snackoverflow.constant;

/**
 * API path constants for the application
 * Centralized place for all API paths to ensure consistency
 */
public final class ApiPath {
    // Base paths
    public static final String API = "/api";
    
    // Auth related paths
    public static final String AUTH = API + "/auth";
    public static final String REGISTER = AUTH + "/register";
    public static final String AUTHENTICATE = AUTH + "/authenticate";
    public static final String RENEW_TOKEN = AUTH + "/renew";
    
    // Product related paths
    public static final String PRODUCTS = "/products";
    public static final String PRODUCT_CLASSES = "/product-classes";
    public static final String PRODUCT_COMMITS = "/product-commits";
    
    // Booking related paths
    public static final String BOOKINGS = "/bookings";
    
    // Member related paths
    public static final String MEMBERS = "/members";
    
    // File related paths
    public static final String FILE_UPLOAD = "/files";
    
    // Prevent instantiation
    private ApiPath() {
        throw new AssertionError("Cannot instantiate constant class");
    }
}