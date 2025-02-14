package com.axreng.backend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Utility class for fetching web content from URLs.
 * <p>
 * This class provides a method to retrieve the content of a web page
 * via an HTTP GET request, handling redirects and logging errors.
 * </p>
 *
 * <h3>Example Usage:</h3>
 * <pre>
 * String content = UrlFetcher.fetchContent("https://ibm.com");
 * System.out.println(content);
 * </pre>
 *
 * <h3>Features:</h3>
 * <ul>
 *     <li>Makes an HTTP GET request to fetch web page content.</li>
 *     <li>Follows redirects automatically.</li>
 *     <li>Logs errors if the request fails.</li>
 * </ul>
 *
 * @author Jean Fernandes
 * @version 1.0
 */
public class UrlFetcher {

    private static final Logger logger = LoggerFactory.getLogger(UrlFetcher.class);

    /**
     * Fetches the content of a web page from the given URL.
     * <p>
     * This method performs an HTTP GET request to retrieve the content
     * of the specified URL. If the response is a redirect (3xx),
     * it follows the redirect and attempts to fetch the content again.
     * </p>
     *
     * @param urlString The URL to retrieve content from.
     * @return The content of the page as a string, or {@code null} if an error occurs.
     */
    public static String fetchContent(String urlString) {
        HttpURLConnection conn = null;

        try {
            var url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = conn.getResponseCode();

            if (responseCode >= 300 && responseCode < 400) {
                String newUrl = conn.getHeaderField("Location");
                if (newUrl != null) {
                    logger.warn("Redirected to: {}", newUrl);
                    return fetchContent(newUrl);
                }
            }

            if (responseCode != HttpURLConnection.HTTP_OK) {
                logger.error("Failed to fetch content. HTTP response code: {} - URL: {}", responseCode, urlString);
                return null;
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                var content = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null)
                    content.append(line).append("\n");

                return content.toString().isEmpty() ? null : content.toString();
            }

        } catch (IOException e) {
            logger.error("Error accessing URL: {} - Message: {}", urlString, e.getMessage());
            return null;
        } finally {
            if (conn != null) conn.disconnect();
        }
    }
}
