package com.axreng.backend.controller;

import com.axreng.backend.dto.ErrorResponse;
import com.axreng.backend.service.SiteQueryProcessorService;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.time.Instant;
import java.util.Optional;

import static com.axreng.backend.util.Constants.*;

/**
 * Controller responsible for retrieving search results based on a given search ID.
 * <p>
 * Exposes an endpoint to fetch the current status and results of a search operation.
 * </p>
 *
 * <h3>Endpoints:</h3>
 * <ul>
 *     <li><b>GET /crawl/{searchId}</b> - Retrieves the current search status and results.</li>
 * </ul>
 *
 * <h3>Response Formats:</h3>
 * <ul>
 *     <li><b>200 OK</b> - Search result successfully retrieved.</li>
 *     <li><b>404 Not Found</b> - If the search ID is not found or null.</li>
 * </ul>
 *
 * Example JSON Response:
 * <pre>
 * {
 *   "id": "30vbllyb",
 *   "status": "active",
 *   "urls": [
 *     "http://hiring.axreng.com/index2.html",
 *     "http://hiring.axreng.com/htmlman1/chcon.1.html"
 *   ]
 * }
 * </pre>
 *
 * @author Jean Fernandes
 * @version 1.2
 */
public class SearchResultFetcherController {

    private static final Logger logger = LoggerFactory.getLogger(SearchResultFetcherController.class);

    /**
     * Initializes the search result fetcher controller and defines the endpoints.
     */
    public SearchResultFetcherController() {

        logger.info("SearchResultFetcherController instantiated");

        Spark.get("/crawl/:id", (req, res) -> {
            res.type(CONTENT_TYPE_JSON);

            return Optional.ofNullable(req.params("id"))
                    .map(searchId -> Optional.ofNullable(SiteQueryProcessorService.getSearchResult(searchId))
                            .map(result -> {
                                res.status(HttpStatus.OK_200);
                                return gson.toJson(result);
                            })
                            .orElseGet(() -> {
                                res.status(HttpStatus.NOT_FOUND_404);
                                return gson.toJson(new ErrorResponse(SEARCH_ID_NOT_FOUND));
                            }))
                    .orElseGet(() -> {
                        res.status(HttpStatus.NOT_FOUND_404);
                        return gson.toJson(new ErrorResponse(SEARCH_ID_CANNOT_BE_NULL));
                    });
        });
    }
}
