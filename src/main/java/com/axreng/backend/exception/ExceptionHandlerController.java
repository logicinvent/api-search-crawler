package com.axreng.backend.controller;

import com.axreng.backend.dto.ErrorResponse;
import com.axreng.backend.exception.MissingBaseUrlException;
import org.eclipse.jetty.http.HttpStatus;
import spark.ExceptionHandler;
import spark.Spark;

import static com.axreng.backend.util.Constants.CONTENT_TYPE_JSON;
import static com.axreng.backend.util.Constants.gson;

/**
 * Configures global exception handling for REST API responses.
 *
 * @author Jean Fernandes
 */
public class ExceptionHandlerController {

    public static void configureExceptionHandling() {
        Spark.exception(MissingBaseUrlException.class, (exception, request, response) -> {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            response.type(CONTENT_TYPE_JSON);
            response.body(gson.toJson(new ErrorResponse(exception.getMessage())));
        });
    }
}
