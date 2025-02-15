package com.axreng.backend.controller;

import com.axreng.backend.dto.ErrorResponse;
import com.axreng.backend.dto.SearchQueryRequestDto;
import com.axreng.backend.service.SiteQueryProcessorService;
import com.axreng.backend.util.StringsUtil;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.time.Instant;
import java.util.Optional;

import static com.axreng.backend.util.Constants.*;

/**
 * Handles site queries via the <code>/crawl</code> endpoint.
 * <p>
 * This controller provides an API endpoint that allows users to initiate a search process
 * based on a specified keyword. The search operation is processed asynchronously, and a
 * unique search ID is returned upon successful initiation.
 * </p>
 *
 * <h2>Endpoints:</h2>
 * <ul>
 *     <li><b>POST /crawl</b> - Starts a new search operation.</li>
 * </ul>
 *
 * <h2>Request Format:</h2>
 * The request must be a JSON object containing a valid keyword:
 * <pre>
 * {
 *   "keyword": "security"
 * }
 * </pre>
 * <p>
 * The keyword must meet the validation criteria:
 * </p>
 * <ul>
 *     <li>Must not be null or empty.</li>
 *     <li>Must contain between 4 and 32 characters.</li>
 * </ul>
 *
 * <h2>Response Formats:</h2>
 * <ul>
 *     <li><b>200 OK</b> - The search process has started successfully. Returns a unique search ID.</li>
 *     <li><b>400 Bad Request</b> - Returned if the request is invalid due to a missing, empty, or improperly formatted keyword.</li>
 * </ul>
 *
 * <h2>Example JSON Response:</h2>
 * <pre>
 * {
 *   "id": "30vbllyb"
 * }
 * </pre>
 *
 * <h2>Possible Error Responses:</h2>
 * <pre>
 * {
 *   "error": "Keyword cannot be null or empty"
 * }
 * </pre>
 * <pre>
 * {
 *   "error": "The search term must be between 4 and 32 characters."
 * }
 * </pre>
 *
 * @author Jean Fernandes
 * @version 1.3
 */

public class SiteQueryProcessorController {

    private static final Logger logger = LoggerFactory.getLogger(SiteQueryProcessorController.class);

    /**
     * Initializes the site query processor controller and defines the endpoints.
     */
    public SiteQueryProcessorController() {

        logger.info("SiteQueryProcessorController instantiated");

        Spark.post("/crawl", (req, res) -> {
            res.type(CONTENT_TYPE_JSON);

            return Optional.ofNullable(gson.fromJson(req.body(), SearchQueryRequestDto.class))
                    .map(SearchQueryRequestDto::getKeyword)
                    .filter(keyword -> !keyword.isBlank())
                    .map(keyword -> {
                        if (!StringsUtil.isValidTerm(keyword)) {
                            res.status(HttpStatus.BAD_REQUEST_400);
                            return gson.toJson(new ErrorResponse(THE_SEARCH_TERM_MUST_BE_BETWEEN_4_AND_32_CHARACTERS));
                        }
                        var service = new SiteQueryProcessorService(keyword);
                        res.status(HttpStatus.OK_200);
                        return gson.toJson(service.startSearchAsync());
                    })
                    .orElseGet(() -> {
                        res.status(HttpStatus.BAD_REQUEST_400);
                        return gson.toJson(new ErrorResponse(KEYWORD_CANNOT_BE_NULL_OR_EMPTY));
                    });
        });
    }
}
