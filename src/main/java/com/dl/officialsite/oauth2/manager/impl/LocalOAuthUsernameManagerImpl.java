package com.dl.officialsite.oauth2.manager.impl;

import com.dl.officialsite.oauth2.manager.OAuthUsernameManager;
import com.dl.officialsite.oauth2.model.bo.user.IUserInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//WARNING: DO NOT PUT IT INTO PRODUCTION
@Component
public class LocalOAuthUsernameManagerImpl implements OAuthUsernameManager {

    private Map<String, ConcurrentHashMap<String, IUserInfo>> storage = new HashMap<>();

    @Override
    public void put(String registrationId, String address, IUserInfo userInfo) {
        if(!storage.containsKey(registrationId)){
            synchronized (this){
                if(!storage.containsKey(registrationId)){
                    storage.put(registrationId, new ConcurrentHashMap<>());
                }
            }
        }

        storage.get(registrationId).put(address, userInfo);
    }

    @Override
    public IUserInfo get(String registrationId, String address) {
        if(!storage.containsKey(registrationId)){
            synchronized (this){
                if(!storage.containsKey(registrationId)){
                    storage.put(registrationId, new ConcurrentHashMap<>());
                }
            }
        }

        return storage.get(registrationId).get(address);
    }
}
