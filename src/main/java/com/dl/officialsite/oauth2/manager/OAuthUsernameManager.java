package com.dl.officialsite.oauth2.manager;

import com.dl.officialsite.oauth2.model.bo.user.IUserInfo;
import org.springframework.stereotype.Component;

@Component
public interface OAuthUsernameManager {

    void put(String registrationId, String address, IUserInfo userInfo);

    IUserInfo get(String registrationId, String address);
}
