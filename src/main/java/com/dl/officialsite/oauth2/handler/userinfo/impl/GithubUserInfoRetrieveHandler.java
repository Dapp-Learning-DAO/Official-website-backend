package com.dl.officialsite.oauth2.handler.userinfo.impl;

import com.dl.officialsite.oauth2.registrations.CommonRegistrationEnum;
import com.dl.officialsite.oauth2.handler.userinfo.IUserInfoRetrieveHandler;
import com.dl.officialsite.oauth2.model.bo.user.GithubUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GithubUserInfoRetrieveHandler implements IUserInfoRetrieveHandler<GithubUserInfo> {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Refer:
     * https://docs.github.com/en/free-pro-team@latest/rest/users/users?apiVersion=2022-11-28#get-the-authenticated-user
     * @param userInfoUri
     * @param accessToken
     * @return
     */
    @Override
    public GithubUserInfo retrieve(String userInfoUri, String accessToken) {
        RequestEntity requestEntity = RequestEntity.get(userInfoUri).
                        header("Authorization","Bearer "+accessToken)
                .build();

        ResponseEntity<GithubUserInfo> responseEntity = restTemplate.exchange(requestEntity,GithubUserInfo.class);

        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();
    }

    @Override
    public String getRegistrationId() {
        return CommonRegistrationEnum.Github.toString();
    }
}
