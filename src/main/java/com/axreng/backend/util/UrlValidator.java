package com.axreng.backend.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * Utility class for validating URLs.
 * <p>
 * This class provides methods to check whether a URL is valid, ensuring
 * that it belongs to a specified domain and optionally allowing subdomains.
 * </p>
 *
 * <h3>Example Usage:</h3>
 * <pre>
 * boolean isValid = UrlValidator.isValid("https://sub.ibm.com", "https://ibm.com", true);
 * System.out.println(isValid); // Output: true
 * </pre>
 *
 * <h3>Features:</h3>
 * <ul>
 *     <li>Validates URLs to ensure they belong to the specified base domain.</li>
 *     <li>Supports an option to allow or restrict subdomains.</li>
 *     <li>Filters out invalid links (e.g., mailto, JavaScript, internal anchors).</li>
 * </ul>
 *
 * @author Jean Fernandes
 * @version 1.0
 */
public class UrlValidator {

    /**
     * Checks if a given URL is valid and belongs to the allowed base domain.
     * <p>
     * This method validates if the provided link is a valid URL and ensures
     * it matches the given base URL. It can also determine whether subdomains
     * are allowed.
     * </p>
     *
     * @param link            The URL to validate.
     * @param baseUrl         The base URL to compare against.
     * @param allowSubdomains Whether subdomains should be considered valid.
     * @return {@code true} if the link is valid and belongs to the base domain; otherwise, {@code false}.
     */
    public static boolean isValid(String link, String baseUrl, boolean allowSubdomains) {
        if (Objects.isNull(link) || isInvalidLink(link))
            return false;

        try {
            var linkUri = new URI(link);
            var baseUri = new URI(baseUrl);

            String linkHost = linkUri.getHost();
            String baseHost = baseUri.getHost();

            if (linkHost == null)
                return true;

            if (!allowSubdomains)
                return linkHost.equals(baseHost);

            return linkHost.equals(baseHost) || linkHost.endsWith("." + baseHost);

        } catch (URISyntaxException e) {
            return false;
        }
    }

    /**
     * Determines if a link is invalid based on common patterns.
     * <p>
     * This method identifies links that should be ignored, such as:
     * <ul>
     *     <li>Email links (mailto:)</li>
     *     <li>JavaScript-based links (javascript:)</li>
     *     <li>Phone links (tel:)</li>
     *     <li>Internal anchors (#)</li>
     * </ul>
     * </p>
     *
     * @param link The URL to check.
     * @return {@code true} if the link is invalid, otherwise {@code false}.
     */
    private static boolean isInvalidLink(String link) {
        return link.startsWith("mailto:")
                || link.startsWith("javascript:")
                || link.startsWith("tel:")
                || link.startsWith("#");
    }
}
