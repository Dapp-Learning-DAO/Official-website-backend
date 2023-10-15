package com.dl.officialsite.oauth2.handler.bind.impl;

import com.dl.officialsite.oauth2.handler.bind.IOAuthBindHandler;
import com.dl.officialsite.oauth2.manager.OAuthUsernameManager;
import com.dl.officialsite.oauth2.model.bo.user.GithubUserInfo;
import com.dl.officialsite.oauth2.registrations.CommonRegistrationEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class GithubOAuthBindHandlerImpl implements IOAuthBindHandler<GithubUserInfo> {

    @Autowired
    private OAuthUsernameManager oAuthUsernameManager;
    @Override
    public void bind(String address, GithubUserInfo githubUser) {
        oAuthUsernameManager.put(this.getRegistrationId(), address, githubUser);
    }

    @Override
    public String getRegistrationId() {
        return CommonRegistrationEnum.Github.toString();
    }
}
