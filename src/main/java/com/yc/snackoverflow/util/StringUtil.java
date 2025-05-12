package com.yc.snackoverflow.util;

import org.apache.commons.lang3.StringUtils;

/**
 * String utility methods
 */
public final class StringUtil {

    /**
     * Check if a string is null, empty or only whitespace
     * 
     * @param str The string to check
     * @return true if the string is null, empty or only whitespace
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Check if a string is not null, empty or only whitespace
     * 
     * @param str The string to check
     * @return true if the string is not null, empty or only whitespace
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    /**
     * Get the default value if the string is empty
     * 
     * @param str The string to check
     * @param defaultValue The default value
     * @return The string if not empty, otherwise the default value
     */
    public static String defaultIfEmpty(String str, String defaultValue) {
        return isEmpty(str) ? defaultValue : str;
    }
    
    /**
     * Truncate a string to a maximum length
     * 
     * @param str The string to truncate
     * @param maxLength The maximum length
     * @return The truncated string
     */
    public static String truncate(String str, int maxLength) {
        if (str == null) {
            return null;
        }
        return str.length() <= maxLength ? str : str.substring(0, maxLength);
    }
    
    /**
     * Convert snake_case to camelCase
     * 
     * @param snakeCase The snake_case string
     * @return The camelCase string
     */
    public static String snakeToCamel(String snakeCase) {
        if (isEmpty(snakeCase)) {
            return snakeCase;
        }
        
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        
        for (char c : snakeCase.toCharArray()) {
            if (c == '_') {
                nextUpper = true;
            } else {
                result.append(nextUpper ? Character.toUpperCase(c) : c);
                nextUpper = false;
            }
        }
        
        return result.toString();
    }
    
    /**
     * Convert camelCase to snake_case
     * 
     * @param camelCase The camelCase string
     * @return The snake_case string
     */
    public static String camelToSnake(String camelCase) {
        if (isEmpty(camelCase)) {
            return camelCase;
        }
        
        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";
        return camelCase.replaceAll(regex, replacement).toLowerCase();
    }
    
    // Prevent instantiation
    private StringUtil() {
        throw new AssertionError("Cannot instantiate utility class");
    }
}