package com.axreng.backend.dto;

public class SearchQueryRequestDto {

    private final String keyword;

    public SearchQueryRequestDto(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
