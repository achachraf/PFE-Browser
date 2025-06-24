package com.browser.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;


public class HttpClient {
    private final Executor executor;

    public HttpClient() {
        // Create a thread pool for handling network requests
        this.executor = Executors.newFixedThreadPool(4);
    }

    public void sendGetRequest(String urlString, Consumer<HttpResponse> callback) {
        final String finalUrlString = urlString.startsWith("http://") || urlString.startsWith("https://") 
                ? urlString 
                : "https://" + urlString;
                
        CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL(finalUrlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                
                final int statusCode = connection.getResponseCode();
                
                // Read the response body
                StringBuilder content = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                            statusCode >= 400 
                                ? connection.getErrorStream() 
                                : connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                }
                
                return new HttpResponse(statusCode, content.toString(), connection.getHeaderFields());
                
            } catch (Exception e) {
                // Return an error response
                return new HttpResponse(500, "<html><body><h1>Error: " + e.getMessage() + "</h1></body></html>", null);
            }
        }, executor).thenAccept(callback);
    }

    // Inner class to represent an HTTP response
    public static class HttpResponse {
        private final int statusCode;
        private final String body;
        private final java.util.Map<String, java.util.List<String>> headers;
        
        public HttpResponse(int statusCode, String body, java.util.Map<String, java.util.List<String>> headers) {
            this.statusCode = statusCode;
            this.body = body;
            this.headers = headers;
        }
        
        public boolean isSuccessful() {
            return statusCode >= 200 && statusCode < 300;
        }
        
        public int getStatusCode() {
            return statusCode;
        }
        
        public String getBody() {
            return body;
        }
        
        public java.util.Map<String, java.util.List<String>> getHeaders() {
            return headers;
        }
    }
}
