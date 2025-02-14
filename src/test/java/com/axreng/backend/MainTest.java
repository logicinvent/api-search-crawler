package com.axreng.backend;

import com.axreng.backend.controller.SearchResultFetcherController;
import com.axreng.backend.controller.SiteQueryProcessorController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void testMainExecution() {
        System.setProperty("BASE_URL", "https://www.ibm.com");
        assertEquals("https://www.ibm.com", System.getProperty("BASE_URL"));
        assertDoesNotThrow(SearchResultFetcherController::new);
        assertDoesNotThrow(SiteQueryProcessorController::new);
    }
}
