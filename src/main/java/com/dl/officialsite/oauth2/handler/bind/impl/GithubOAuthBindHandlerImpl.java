package com.dl.officialsite.oauth2.handler.bind.impl;

import com.dl.officialsite.oauth2.handler.bind.IOAuthBindHandler;
import com.dl.officialsite.oauth2.model.bo.user.GithubUserInfo;
import com.dl.officialsite.oauth2.registrations.CommonRegistrationEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@Slf4j
public class GithubOAuthBindHandlerImpl implements IOAuthBindHandler<GithubUserInfo> {

    @Override
    public void bind(String address, GithubUserInfo githubUser) {
        //TODO
    }

    @Override
    public String getRegistrationId() {
        return CommonRegistrationEnum.Github.toString();
    }
}
