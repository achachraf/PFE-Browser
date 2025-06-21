package com.browser.util;

public class UrlValidator {
    
    /**
     * Validates if a given string is a valid URL
     * 
     * @param url The URL string to validate
     * @return true if the URL is valid, false otherwise
     */
    public static boolean isValid(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        
        // Check if URL starts with valid protocol
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return false;
        }
        
        // Basic pattern check
        String urlRegex = "^(https?://)"                // Protocol
                + "((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|"  // Domain name
                + "((\\d{1,3}\\.){3}\\d{1,3}))"         // OR IP address
                + "(:\\d+)?(\\/[-a-z\\d%_.~+]*)*"       // Port and path
                + "(\\?[;&a-z\\d%_.~+=-]*)?"           // Query string
                + "(\\#[-a-z\\d_]*)?$";               // Fragment locator
        
        return url.matches(urlRegex);
    }
    
    /**
     * Normalizes a URL by adding https:// if missing
     * 
     * @param url The URL string to normalize
     * @return The normalized URL string
     */
    public static String normalize(String url) {
        if (url == null || url.trim().isEmpty()) {
            return "";
        }
        
        // Add https:// prefix if missing
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "https://" + url;
        }
        
        return url;
    }
}
