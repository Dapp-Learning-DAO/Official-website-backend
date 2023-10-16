package com.dl.officialsite.oauth2.handler.bind;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class OAuthBindHandlers {

    @Autowired
    private List<IOAuthBindHandler> oauthBindHandlers;

    public IOAuthBindHandler get(String registrationId){
        for(IOAuthBindHandler handler: this.oauthBindHandlers){
            if(Objects.equals(handler.getRegistrationId(), registrationId)){
                return handler;
            }
        }
        throw new IllegalArgumentException("registrationId");
    }
}
