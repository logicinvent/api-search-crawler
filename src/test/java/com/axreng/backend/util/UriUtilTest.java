package com.axreng.backend.util;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UriUtilTest {

    private final UriUtil uriUtil = new UriUtil();

    @Test
    void shouldExtractBaseUrl() {
        String baseUrl = uriUtil.extractBaseUrl("https://ibm.com/path/page.html");
        assertThat(baseUrl, is(equalTo("https://ibm.com")));
    }

    @Test
    void shouldThrowExceptionForInvalidUrl() {
        assertThrows(IllegalArgumentException.class, () -> uriUtil.extractBaseUrl("invalid-url"));
        assertThrows(IllegalArgumentException.class, () -> uriUtil.extractBaseUrl("://missing.scheme.com"));
    }
}
