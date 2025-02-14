package com.axreng.backend.util;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.axreng.backend.util.Constants.ID_LENGTH;
import static com.axreng.backend.util.Constants.RANDOM;

/**
 * Utility class for generating random alphanumeric search IDs.
 * <p>
 * This class provides a method to generate a unique identifier composed of
 * uppercase letters, lowercase letters, and digits, ensuring uniqueness
 * for each search process.
 * </p>
 *
 * <h3>Example Usage:</h3>
 * <pre>
 * SearchIdGenerator generator = new SearchIdGenerator();
 * String searchId = generator.generateSearchId();
 * System.out.println(searchId); // Output: "A1bC2dE3F"
 * </pre>
 *
 * <h3>Characteristics:</h3>
 * <ul>
 *     <li>Uses a fixed length for the ID, defined in {@link Constants#ID_LENGTH}.</li>
 *     <li>Generates a secure random alphanumeric string.</li>
 *     <li>Ensures uniqueness with {@link Constants#RANDOM}.</li>
 * </ul>
 *
 * @author Jean Fernandes
 * @version 1.0
 */
public class SearchIdGenerator {

    /**
     * Alphanumeric character set used to generate the search ID.
     * <p>
     * This string contains:
     * <ul>
     *     <li>Digits (0-9)</li>
     *     <li>Uppercase letters (A-Z)</li>
     *     <li>Lowercase letters (a-z)</li>
     * </ul>
     * </p>
     */
    private static final String ALPHA_NUMERIC_STRING = IntStream.concat(
                    IntStream.rangeClosed('0', '9'),
                    IntStream.concat(
                            IntStream.rangeClosed('A', 'Z'),
                            IntStream.rangeClosed('a', 'z')
                    )
            ).mapToObj(c -> String.valueOf((char) c))
            .collect(Collectors.joining());

    /**
     * Generates a random alphanumeric search ID of a fixed length.
     * <p>
     * The generated ID consists of random characters chosen from
     * {@link #ALPHA_NUMERIC_STRING}.
     * </p>
     *
     * @return A randomly generated search ID of length {@link Constants#ID_LENGTH}.
     */
    public String generateSearchId() {
        return IntStream.range(0, ID_LENGTH)
                .mapToObj(i -> String.valueOf(
                        ALPHA_NUMERIC_STRING.charAt(RANDOM.nextInt(ALPHA_NUMERIC_STRING.length()))))
                .collect(Collectors.joining());
    }
}
