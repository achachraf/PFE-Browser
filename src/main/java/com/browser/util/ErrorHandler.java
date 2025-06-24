package com.browser.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Utility class for handling errors in the browser
 */
public class ErrorHandler {

    /**
     * Displays an error message to the user
     * 
     * @param errorType The type of error
     * @param message The error message to display
     */
    public static void showError(ErrorType errorType, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Browser Error");
        alert.setHeaderText(errorType.getTitle());
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Creates a user-friendly error page for HTTP errors
     * 
     * @param statusCode The HTTP status code
     * @param errorMessage Additional error details
     * @return HTML content for the error page
     */
    public static String createErrorPage(int statusCode, String errorMessage) {
        String title = getErrorTitle(statusCode);
        String description = getErrorDescription(statusCode);
        
        return "<html><head><style>"
             + "body { font-family: Arial, sans-serif; text-align: center; padding: 50px; }"
             + "h1 { color: #d9534f; }"
             + "h2 { color: #333; }"
             + "p { color: #777; }"
             + ".container { max-width: 600px; margin: 0 auto; }"
             + "</style></head>"
             + "<body><div class='container'>"
             + "<h1>Error " + statusCode + "</h1>"
             + "<h2>" + title + "</h2>"
             + "<p>" + description + "</p>"
             + (errorMessage != null ? "<p><em>" + errorMessage + "</em></p>" : "")
             + "</div></body></html>";
    }
    
    private static String getErrorTitle(int statusCode) {
        switch (statusCode) {
            case 400: return "Bad Request";
            case 401: return "Unauthorized";
            case 403: return "Forbidden";
            case 404: return "Page Not Found";
            case 500: return "Internal Server Error";
            case 502: return "Bad Gateway";
            case 503: return "Service Unavailable";
            case 504: return "Gateway Timeout";
            default: return "An Error Occurred";
        }
    }
    
    private static String getErrorDescription(int statusCode) {
        switch (statusCode) {
            case 400: return "The server could not understand the request due to invalid syntax.";
            case 401: return "Authentication is required to access this resource.";
            case 403: return "You don't have permission to access this resource.";
            case 404: return "The page you were looking for could not be found.";
            case 500: return "The server encountered an internal error and was unable to complete your request.";
            case 502: return "The server received an invalid response from an upstream server.";
            case 503: return "The server is temporarily unable to handle the request.";
            case 504: return "The server was acting as a gateway and did not receive a timely response.";
            default: return "An unexpected error occurred while processing your request.";
        }
    }
    
    /**
     * Enum representing different types of browser errors
     */
    public enum ErrorType {
        NETWORK("Network Error"),
        SECURITY("Security Error"),
        RENDERING("Rendering Error"),
        JAVASCRIPT("JavaScript Error"),
        NAVIGATION("Navigation Error");
        
        private final String title;
        
        ErrorType(String title) {
            this.title = title;
        }
        
        public String getTitle() {
            return title;
        }
    }
}
