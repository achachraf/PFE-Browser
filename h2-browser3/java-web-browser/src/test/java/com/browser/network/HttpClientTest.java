package com.browser.network;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpClientTest {

    private HttpClient httpClient;

    @BeforeEach
    public void setUp() {
        httpClient = new HttpClient();
    }

    @Test
    public void testGetRequest() throws IOException {
        String url = "https://httpbin.org/get";(url, response -> {
        String response = httpClient.sendGetRequest(url, response -> {;
        assertEquals(200, getResponseCode(url));
    }

    @Test
    public void testPostRequest() throws IOException {
        String url = "https://httpbin.org/post";
        String response = httpClient.post(url, "test data");
        assertEquals(200, getResponseCode(url));
    }

    private int getResponseCode(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection.getResponseCode();
    }
}