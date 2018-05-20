package com.cdy.client2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 陈东一
 * 2018/1/28 16:07
 */
@Controller
public class UserController {
    
    @RequestMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect(UrlContants.server + "/tologin?redirect=" + UrlContants.client2 + "/user");
    }
    
    @RequestMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        response.sendRedirect(UrlContants.server + "/logout");
    }
    
    @RequestMapping("/user")
    @ResponseBody
    public String user(HttpServletRequest request) {
       
        String username = CookieUtil.getCookieAttribute("username-client2", request);
        System.out.println(username);
        return username;
    }
}
