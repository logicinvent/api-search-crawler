package com.axreng.backend.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UrlValidatorTest {

    private final String baseUrl = "https://ibm.com";

    @Test
    void shouldValidateUrlWithinSameDomain() {
        assertTrue(UrlValidator.isValid("https://ibm.com/page", baseUrl, false));
    }

    @Test
    void shouldValidateSubdomainWhenAllowed() {
        assertTrue(UrlValidator.isValid("https://sub.ibm.com/page", baseUrl, true));
    }

    @Test
    void shouldInvalidateSubdomainWhenNotAllowed() {
        assertFalse(UrlValidator.isValid("https://sub.ibm.com/page", baseUrl, false));
    }

    @Test
    void shouldInvalidateInvalidSchemes() {
        assertFalse(UrlValidator.isValid("mailto:user@ibm.com", baseUrl, true));
        assertFalse(UrlValidator.isValid("javascript:void(0);", baseUrl, true));
        assertFalse(UrlValidator.isValid("tel:+1234567890", baseUrl, true));
    }

    @Test
    void shouldInvalidateAnchorLinks() {
        assertFalse(UrlValidator.isValid("#section1", baseUrl, true));
    }

    @Test
    void shouldInvalidateNullAndEmptyUrls() {
        assertFalse(UrlValidator.isValid(null, baseUrl, true));
    }

    @Test
    void shouldInvalidateMalformedUrls() {
        assertFalse(UrlValidator.isValid("ht@tp://invalid-url", baseUrl, true));
    }

    @Test
    void shouldValidateRelativePaths() {
        assertTrue(UrlValidator.isValid("/about", baseUrl, true));
    }
}
