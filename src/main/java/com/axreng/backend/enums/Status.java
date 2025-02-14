package com.axreng.backend.enums;

/**
 * Enum representing possible status values.
 */
public enum Status {

    ACTIVE("Active"),
    FAILED("Failed"),
    DONE("Done"),;

    private final String value;

    Status(String value) {
        this.value = value;
    }

    /**
     * Gets the string representation of the status.
     *
     * @return The status value as a string.
     */
    public String getValue() {
        return value;
    }
}
