package com.axreng.backend.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import static com.axreng.backend.util.Constants.ALLOW_SUBDOMAINS;
import static com.axreng.backend.util.Constants.LINK_PATTERN;

/**
 * Utility class for handling URL extraction and resolution.
 */
public class UriUtil {

    /**
     * Extracts the base URL (scheme + host) from a given URL.
     *
     * @param url The full URL.
     * @return The base URL (e.g., "https://ibm.com").
     * @throws IllegalArgumentException If the URL is invalid.
     */
    public String extractBaseUrl(String url) {
        try {
            URI uri = new URI(url);
            if (uri.getScheme() == null || uri.getHost() == null)
                throw new IllegalArgumentException("Invalid start URL: " + url);

            return uri.getScheme() + "://" + uri.getHost();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid start URL: " + url, e);
        }
    }

    /**
     * Extracts and resolves links from the HTML content.
     *
     * @param currentUrl The base URL of the current page.
     * @param html       The HTML content to scan for links.
     * @param baseUrl    The main domain to validate allowed links.
     * @return A set of valid, resolved URLs.
     */
    public Set<String> extractLinks(String currentUrl, String html, String baseUrl) {
        Set<String> links = new HashSet<>();
        Matcher matcher = LINK_PATTERN.matcher(html);

        while (matcher.find()) {
            String link = matcher.group(1);

            if (!UrlValidator.isValid(link, baseUrl, ALLOW_SUBDOMAINS))
                continue;

            String absoluteUrl = resolveUrl(currentUrl, link);

            if (absoluteUrl != null)
                links.add(absoluteUrl);

        }

        return links;
    }

    /**
     * Resolves a relative or absolute URL based on a given base URL.
     *
     * @param currentUrl The base URL.
     * @param link       The link to resolve.
     * @return The fully resolved URL as a string, or null if invalid.
     */
    private String resolveUrl(String currentUrl, String link) {
        try {
            link = sanitizeUrl(link);
            URI baseUri = new URI(currentUrl);
            URI resolvedUri = baseUri.resolve(link);

            if (resolvedUri.getScheme() == null || resolvedUri.getHost() == null)
                return null;

            return resolvedUri.toString();
        } catch (URISyntaxException e) {
            return null;
        }
    }

    /**
     * Sanitizes a URL by replacing spaces and other illegal characters.
     *
     * @param url The original URL.
     * @return A sanitized URL string.
     */
    private String sanitizeUrl(String url) {
        if (url == null) return null;

        try {
            return new URI(url.trim().replace(" ", "%20")).toString();
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
