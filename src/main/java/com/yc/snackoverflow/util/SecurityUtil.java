package com.yc.snackoverflow.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Security utility methods
 */
public final class SecurityUtil {

    /**
     * Get the current authenticated user's username
     * 
     * @return The username of the current authenticated user, or null if no user is authenticated
     */
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        
        return principal.toString();
    }
    
    /**
     * Check if the current user has a specific role
     * 
     * @param role The role to check
     * @return true if the current user has the specified role
     */
    public static boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        
        String rolePrefix = "ROLE_";
        String roleWithPrefix = role.startsWith(rolePrefix) ? role : rolePrefix + role;
        
        return authentication.getAuthorities().stream()
                .anyMatch(a -> roleWithPrefix.equals(a.getAuthority()));
    }
    
    /**
     * Check if the current user is authenticated
     * 
     * @return true if the current user is authenticated
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
    
    // Prevent instantiation
    private SecurityUtil() {
        throw new AssertionError("Cannot instantiate utility class");
    }
}