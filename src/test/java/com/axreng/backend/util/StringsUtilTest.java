package com.axreng.backend.util;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class StringsUtilTest {

    @Test
    void shouldReturnTrueForValidTerm() {
        assertThat(StringsUtil.isValidTerm("validTerm"), is(true));
        assertThat(StringsUtil.isValidTerm("abcd"), is(true)); // Minimum valid length
        assertThat(StringsUtil.isValidTerm("12345678901234567890123456789012"), is(true)); // Maximum valid length
    }

    @Test
    void shouldReturnFalseForNullOrEmptyTerm() {
        assertThat(StringsUtil.isValidTerm(null), is(false));
        assertThat(StringsUtil.isValidTerm(""), is(false));
    }

    @Test
    void shouldReturnFalseForShortTerm() {
        assertThat(StringsUtil.isValidTerm("abc"), is(false)); // Less than 4 characters
        assertThat(StringsUtil.isValidTerm("a"), is(false));
    }

    @Test
    void shouldReturnFalseForLongTerm() {
        String longString = "123456789012345678901234567890123"; // 33 characters
        assertThat(StringsUtil.isValidTerm(longString), is(false));
    }

}
