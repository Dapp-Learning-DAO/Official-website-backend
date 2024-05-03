package com.dl.officialsite.oauth2.config;

import com.dl.officialsite.config.bean.Configurable;
import lombok.Data;

@Data
public class OAuthConfig implements Configurable {
	private String clientId;
	private String userAuthorizationUri;
	private String userInfoUri;
	private String clientSecret;
	private String accessTokenUri;
	private String callbackUrl;
}