package com.axreng.backend;

import com.axreng.backend.controller.ExceptionHandlerController;
import com.axreng.backend.controller.SearchResultFetcherController;
import com.axreng.backend.controller.SiteQueryProcessorController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("Starting up...");

        logger.info("Initializing environment variables...");
        gettingBaseUrl();

        logger.info("Initializing controllers...");
        startControllers();

        logger.info("Configuring global exception handling...");
        ExceptionHandlerController.configureExceptionHandling();

        logger.info("All services initialized. System ready");

    }

    private static void startControllers() {
        new SearchResultFetcherController();
        new SiteQueryProcessorController();
    }

    private static void gettingBaseUrl() {

        if (Objects.isNull(System.getenv().get("BASE_URL")))
            System.setProperty("BASE_URL", "https://www.ibm.com");

    }

}
