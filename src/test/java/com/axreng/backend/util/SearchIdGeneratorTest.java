package com.axreng.backend.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

class SearchIdGeneratorTest {

    private SearchIdGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new SearchIdGenerator();
    }

    @Test
    void shouldGenerateSearchId_WithCorrectLength() {
        String searchId = generator.generateSearchId();

        assertThat("Search ID should have the correct length", searchId.length(), is(Constants.ID_LENGTH));
    }

    @Test
    void shouldGenerateSearchId_WithAlphanumericCharactersOnly() {
        String searchId = generator.generateSearchId();

        assertThat("Search ID should contain only alphanumeric characters",
                searchId.matches("^[a-zA-Z0-9]+$"), is(true));
    }

    @Test
    void shouldGenerateUniqueSearchIds() {
        var id1 = generator.generateSearchId();
        var id2 = generator.generateSearchId();

        assertThat("Generated IDs should not be the same", id1, is(not(id2)));
    }
}
