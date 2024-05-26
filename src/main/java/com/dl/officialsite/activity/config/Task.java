package com.dl.officialsite.activity.config;

import lombok.Data;

@Data
public class Task {
    private String name;
    private String target;
    private String targetUrl;
    private String description;
}