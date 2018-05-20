package com.cdy.client1.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * todo
 * Created by 陈东一
 * 2018/5/20 13:51
 */
public class CookieUtil {
    
    public static String getCookieAttribute(String key ,HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if (key.equalsIgnoreCase(cookie.getName())) {
                    String username = cookie.getValue();
                    return username;
                }
            }
        }
        return null;
    }
}
