package com.dl.officialsite.common.utils;

import javax.servlet.http.HttpSession;

public abstract class HttpSessionUtils {

    public static final String MEMBER_ATTRIBUTE_KEY = "member";


//todo
    public static void putMember(HttpSession session, String address){
        session.setAttribute(MEMBER_ATTRIBUTE_KEY, address);
    }


    public static void putMemberWithAddress(HttpSession session, String address){
        session.setAttribute(MEMBER_ATTRIBUTE_KEY+address, address);
    }


    public static  String getMemberWithAddress(HttpSession session, String address) {
        Object sessionObj = session.getAttribute(MEMBER_ATTRIBUTE_KEY+address);
        if(sessionObj == null){
            throw new IllegalArgumentException("User not login");
        }
        return sessionObj.toString();
    }


    public static String getMember(HttpSession session){
        Object sessionObj = session.getAttribute(MEMBER_ATTRIBUTE_KEY);
        if(sessionObj == null){
            throw new IllegalArgumentException("User not login");
        }
        return sessionObj.toString();
    }
}
