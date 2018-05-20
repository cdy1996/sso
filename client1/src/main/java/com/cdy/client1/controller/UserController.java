package com.cdy.client1.controller;

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
        response.sendRedirect(UrlContants.server + "/tologin?redirect=" + UrlContants.client1 + "/user");
    }
    
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
         String token = CookieUtil.getCookieAttribute("client1-token", request);
        response.sendRedirect(UrlContants.server + "/logout?token="+token);
    }
    
    @RequestMapping("/user")
    @ResponseBody
    public String user(HttpServletRequest request) throws IOException {
        String token = request.getParameter("token");
        if(token == null || token.isEmpty()) {
            token = CookieUtil.getCookieAttribute("client1-token", request);
        }
        String result = (String) HttpClientUtil.doGet(UrlContants.server + "/user?token="+token);
        return result;
    }
}
