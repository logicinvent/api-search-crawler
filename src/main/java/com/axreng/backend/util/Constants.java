package com.axreng.backend.util;

import com.axreng.backend.dto.ResultFetcherDto;
import com.google.gson.Gson;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class Constants {

    public static final Gson gson = new Gson();
    public static final String SEARCH_ID_NOT_FOUND = "Search ID not found";
    public static final String SEARCH_ID_CANNOT_BE_NULL = "Search ID cannot be null!";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String KEYWORD_CANNOT_BE_NULL_OR_EMPTY = "Keyword cannot be null or empty";
    public static final String THE_SEARCH_TERM_MUST_BE_BETWEEN_4_AND_32_CHARACTERS = "The search term must be between 4 and 32 characters.";
    public static final int ID_LENGTH = 8;
    public static final int MIN_CHARACTER = 4;
    public static final int MAX_CHARACTER = 32;

    public static final int MAX_PAGES = 100;
    public static final int THREAD_POOL_SIZE = 50;
    public static final Map<String, ResultFetcherDto> searchResults = new ConcurrentHashMap<>();
    public static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public static final SecureRandom RANDOM = new SecureRandom();
    public static final Pattern LINK_PATTERN = Pattern.compile("<a[^>]+href=\"(.*?)\"");
    public static final boolean ALLOW_SUBDOMAINS = true;

}
