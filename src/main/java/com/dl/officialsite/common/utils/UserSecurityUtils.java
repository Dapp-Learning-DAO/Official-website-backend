package com.dl.officialsite.common.utils;

import com.dl.officialsite.login.model.SessionUserInfo;
import com.dl.officialsite.login.model.UserPrincipleData;

public class UserSecurityUtils {

    private static ThreadLocal<UserPrincipleData> userInfoThreadLocal = new ThreadLocal<>();

    public static void setPrincipleLogin(UserPrincipleData userPrincipleData){
        if (userPrincipleData == null){
            throw new IllegalArgumentException("userInfo");
        }
        userInfoThreadLocal.set(userPrincipleData);
    }

    public static UserPrincipleData getUserLogin(){
        return userInfoThreadLocal.get();
    }

    public static boolean isUserLogin(){
        return userInfoThreadLocal.get() != null;
    }

    public static void removeLoginStatus(){
        userInfoThreadLocal.remove();
    }
}
