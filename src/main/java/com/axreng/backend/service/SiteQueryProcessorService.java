package com.axreng.backend.service;

import com.axreng.backend.dto.QueryIdentifierDto;
import com.axreng.backend.dto.ResultFetcherDto;
import com.axreng.backend.enums.Status;
import com.axreng.backend.exception.MissingBaseUrlException;
import com.axreng.backend.util.SearchIdGenerator;
import com.axreng.backend.util.UriUtil;
import com.axreng.backend.util.UrlFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.axreng.backend.util.Constants.*;

/**
 * Service responsible for processing site queries asynchronously by searching for a given term across web pages.
 * Each search runs in a separate thread using a thread pool.
 *
 * @author Jean Fernandes
 * @version 1.3
 */
public class SiteQueryProcessorService {

    private static final Logger logger = LoggerFactory.getLogger(SiteQueryProcessorService.class);

    private final Set<String> visitedUrls;
    private final Queue<String> queue;
    private final String searchTerm;
    private final String baseUrl;
    private final SearchIdGenerator searchIdGenerator;
    private final UriUtil uriUtil;
    private final ResultFetcherDto result;

    /**
     * Initializes the site query processor with the specified search term.
     *
     * @param searchTerm The keyword to search for within the pages.
     * @throws IllegalArgumentException if the base URL is not set in the environment.
     */
    public SiteQueryProcessorService(String searchTerm) {
        logger.info("Initializing site query processor...");

        this.result = new ResultFetcherDto();
        this.visitedUrls = Collections.synchronizedSet(new HashSet<>());
        this.queue = new ConcurrentLinkedQueue<>();
        this.uriUtil = new UriUtil();
        this.searchIdGenerator = new SearchIdGenerator();

        var startUrl = System.getenv().get("BASE_URL");

        if (Objects.isNull(startUrl) || startUrl.isBlank())
            throw new MissingBaseUrlException("BASE_URL environment variable is not set or empty.");

        logger.info("Base URL: {}", startUrl);
        this.searchTerm = searchTerm.toLowerCase();
        this.baseUrl = uriUtil.extractBaseUrl(startUrl);

        logger.info("Adding initial URL to queue: {}", startUrl);
        queue.add(startUrl);
    }

    /**
     * Initiates an asynchronous search process.
     *
     * @return A {@link QueryIdentifierDto} containing the generated search ID.
     */
    public QueryIdentifierDto startSearchAsync() {
        var searchId = searchIdGenerator.generateSearchId();
        result.setId(searchId);
        searchResults.put(searchId, result);

        CompletableFuture.runAsync(this::executeSearch, executorService)
                .whenComplete((res, ex) -> {
                    if (ex != null) {
                        logger.error("Error during search execution: {}", ex.getMessage(), ex);
                        result.setStatus(Status.FAILED);
                    } else {
                        logger.info("Finishing: {}", Instant.now());
                        finalizeSearch();
                    }
                });

        return new QueryIdentifierDto(searchId);
    }

    /**
     * Executes the search process asynchronously.
     */
    private void executeSearch() {
        logger.info("Starting search process asynchronously...");

        int pagesVisited = 0;

        while (!queue.isEmpty() && pagesVisited < MAX_PAGES) {
            String url = queue.poll();

            if (url == null || visitedUrls.contains(url))
                continue;

            processUrl(url);
            pagesVisited++;
        }
    }

    /**
     * Processes a single URL by fetching its content, checking for the search term,
     * and extracting new links to visit.
     *
     * @param url The URL to process.
     */
    private void processUrl(String url) {
        logger.info("Processing URL: {}", url);
        visitedUrls.add(url);

        try {
            var content = UrlFetcher.fetchContent(url);

            if (content == null) {
                logger.warn("Skipping URL due to fetch error: {}", url);
                return;
            }

            if (content.toLowerCase().contains(searchTerm)) {
                result.getUrls().add(url);
                logger.info("Search term '{}' found in: {}", searchTerm, url);
            }

            uriUtil.extractLinks(url, content, baseUrl).forEach(link -> {
                if (!visitedUrls.contains(link) && !queue.contains(link)) {
                    queue.add(link);
                    logger.debug("Added new link to queue: {}", link);
                }
            });

        } catch (Exception e) {
            logger.error("Error accessing URL: {} - Message: {}", url, e.getMessage(), e);
        }
    }

    /**
     * Finalizes the search process by setting the final status and logging statistics.
     */
    private void finalizeSearch() {
        result.setStatus(Status.DONE);
        logger.info("Search completed. Search ID: {}", result.getId());
        logger.info("Total pages visited: {}", visitedUrls.size());
        logger.info("Queue size: {}", queue.size());
        logger.info("Pages containing the term: {}", result.getUrls().size());
    }

    /**
     * Retrieves the current search result for a given search ID.
     *
     * @param searchId The search ID.
     * @return The {@link ResultFetcherDto} containing the current search progress or null if not found.
     */
    public static ResultFetcherDto getSearchResult(String searchId) {
        return searchResults.get(searchId);
    }
}
