package com.axreng.backend.dto;

import com.axreng.backend.enums.Status;

import java.util.ArrayList;
import java.util.List;

public class ResultFetcherDto {

    private String id;
    private Status status;
    private List<String> urls;

    public ResultFetcherDto() {
        this.urls = new ArrayList<>();
        this.status = Status.ACTIVE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
