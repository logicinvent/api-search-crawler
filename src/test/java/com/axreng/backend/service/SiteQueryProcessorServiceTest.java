package com.axreng.backend.service;

import com.axreng.backend.util.UriUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SiteQueryProcessorServiceTest {

    @Test
    void testBaseUrlExtraction() {
        UriUtil uriUtil = new UriUtil();
        String baseUrl = uriUtil.extractBaseUrl("https://ibm.com/page");
        assertEquals("https://ibm.com", baseUrl);
    }
}
