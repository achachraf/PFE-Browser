package com.browser.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UrlValidatorTest {

    private final UrlValidator urlValidator = new UrlValidator();

    @Test
    void testValidUrls() {
        assertTrue(urlValidator.isValid("http://example.com"));
        assertTrue(urlValidator.isValid("https://example.com"));
        assertTrue(urlValidator.isValid("http://www.example.com"));
        assertTrue(urlValidator.isValid("https://www.example.com"));
        assertTrue(urlValidator.isValid("http://example.com/path/to/resource"));
        assertTrue(urlValidator.isValid("https://example.com/path/to/resource"));
    }

    @Test
    void testInvalidUrls() {
        assertFalse(urlValidator.isValid("htp://example.com"));
        assertFalse(urlValidator.isValid("://example.com"));
        assertFalse(urlValidator.isValid("http://"));
        assertFalse(urlValidator.isValid("http://.com"));
        assertFalse(urlValidator.isValid("http://example..com"));
        assertFalse(urlValidator.isValid("http://example.com:abc"));
    }

    @Test
    void testEmptyUrl() {
        assertFalse(urlValidator.isValid(""));
    }

    @Test
    void testNullUrl() {
        assertFalse(urlValidator.isValid(null));
    }
}