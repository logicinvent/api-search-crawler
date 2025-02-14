package com.axreng.backend.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class UrlFetcherTest {


    @Test
    void shouldReturnNullForInvalidUrl() {
        String content = UrlFetcher.fetchContent("invalid-url");
        assertNull(content);
    }

}
