package com.dl.officialsite.oauth2.handler.userinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
public class UserInfoRetrieveHandlers{

    @Autowired
    private List<IUserInfoRetrieveHandler> userInfoRetrieveHandlers;

    public IUserInfoRetrieveHandler get(String registrationId){
        for(IUserInfoRetrieveHandler userInfoHandler: userInfoRetrieveHandlers){
            if(Objects.equals(userInfoHandler.getRegistrationId(), registrationId)){
                return userInfoHandler;
            }
        }
        throw new IllegalArgumentException("registrationId");
    }

}
