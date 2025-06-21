package com.browser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BrowserAppTest {

    private BrowserApp browserApp;

    @BeforeEach
    void setUp() {
        browserApp = new BrowserApp();
    }

    @Test
    void testLaunch() {
        assertDoesNotThrow(() -> browserApp.start(new Stage()));
    }

    @Test
    void testUrlLoading() {
        // Simulate loading a URL and check if the WebEngine loads it correctly
        String testUrl = "https://example.com";
        browserApp.loadUrl(testUrl);
        assertEquals(testUrl, browserApp.getCurrentUrl());
    }

    @Test
    void testInvalidUrlHandling() {
        String invalidUrl = "invalid-url";
        browserApp.loadUrl(invalidUrl);
        assertEquals("https://example.com", browserApp.getCurrentUrl()); // Assuming default behavior
    }

    @Test
    void testHistoryManagement() {
        browserApp.loadUrl("https://example.com");
        browserApp.loadUrl("https://another-example.com");
        assertEquals(2, browserApp.getHistorySize());
    }
}