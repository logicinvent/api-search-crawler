package com.axreng.backend.util;

import java.util.Objects;

import static com.axreng.backend.util.Constants.MAX_CHARACTER;
import static com.axreng.backend.util.Constants.MIN_CHARACTER;

/**
 * Utility class for string validations.
 *
 * <p>
 * This class provides utility methods for validating search terms and ensuring
 * they meet specific length constraints.
 * </p>
 *
 * <h3>Example Usage:</h3>
 * <pre>
 * boolean isValid = StringsUtil.isValidTerm("example"); // Returns true or false
 * </pre>
 *
 * <h3>Validation Rules:</h3>
 * <ul>
 *     <li>The search term must be between 4 and 32 characters long.</li>
 *     <li>Null or empty values are not allowed.</li>
 * </ul>
 *
 * @author Jean Fernandes
 * @version 1.4
 */
public class StringsUtil {

    /**
     * Checks if the given search term is valid based on length constraints.
     *
     * @param searchTerm The search term to validate.
     * @return true if the term meets the requirements, false otherwise.
     */
    public static boolean isValidTerm(String searchTerm) {
        if (Objects.isNull(searchTerm))
            return false;

        int length = searchTerm.length();
        return length >= MIN_CHARACTER && length <= MAX_CHARACTER;
    }
}
