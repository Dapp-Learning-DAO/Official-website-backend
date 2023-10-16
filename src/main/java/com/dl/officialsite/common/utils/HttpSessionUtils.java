package com.dl.officialsite.common.utils;

import javax.servlet.http.HttpSession;

public abstract class HttpSessionUtils {

    public static void putMember(HttpSession session, String address){
        session.setAttribute("address", address);
    }

    public static String getMember(HttpSession session){
        Object sessionObj = session.getAttribute("address");
        if(sessionObj == null){
            throw new IllegalArgumentException("User not login");
        }
        return sessionObj.toString();
    }
}
