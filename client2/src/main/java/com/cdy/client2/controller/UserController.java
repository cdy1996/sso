package com.cdy.client2.controller;

import com.cdy.CookieUtil;
import com.cdy.HttpClientUtil;
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
        response.sendRedirect(Contants.server_url + "/tologin?redirect=" + Contants.client2_url + "/user");
    }
    
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = CookieUtil.getCookie(Contants.client2_url, request);
        response.sendRedirect(Contants.server_url + "/logout?token="+token);
    }
    
    @RequestMapping("/user")
    @ResponseBody
    public String user(HttpServletRequest request) throws IOException {
        String token = request.getParameter("token");
        if(token == null || token.isEmpty()) {
            token = CookieUtil.getCookie(Contants.client2_url, request);
        }
        String result = HttpClientUtil.doGet(Contants.server_url + "/user?token="+token);
        return result;
    }
    
}
