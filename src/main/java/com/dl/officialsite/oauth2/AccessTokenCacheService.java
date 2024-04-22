package com.dl.officialsite.oauth2;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class AccessTokenCacheService {
    private static final Cache<String, String> ACCESS_TOKEN_CACHE = CacheBuilder.newBuilder()
        .expireAfterWrite(6, TimeUnit.MINUTES)
        .build();

    public static void addGitHubAccessToken(String username, String accessToken){
        ACCESS_TOKEN_CACHE.put(gitHubAccessTokenKey(username),accessToken);
    }
    public static String getGitHubAccessToken(String username){
        return ACCESS_TOKEN_CACHE.getIfPresent(gitHubAccessTokenKey(username));
    }

    private static String gitHubAccessTokenKey(String username){
        return String.format("%s_GITHUB_ACCESS_TOKEN", username);
    }

}