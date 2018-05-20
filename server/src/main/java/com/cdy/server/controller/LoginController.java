package com.cdy.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 陈东一
 * 2018/1/28 15:43
 */
@Controller
public class LoginController {

    @RequestMapping("/user")
    @ResponseBody
    public String user(HttpServletRequest request){
        String username = CookieUtil.getCookieAttribute("username", request);
        String password = JedisUtil.get(username);
        return username+"-"+password;
    }
    
    @RequestMapping("/tologin")
    public String toLogin(HttpServletRequest request,HttpServletResponse response, String redirect, Model model) throws IOException {
        String username = CookieUtil.getCookieAttribute("username", request);
        if (username != null) {
            response.sendRedirect(redirect+"username="+username);
            return null;
        }
        model.addAttribute("redirect", redirect);
        return "login";
    }
    
    @RequestMapping("/login")
    public void login(HttpServletRequest request,HttpServletResponse response, String username, String password, String redirect) throws IOException {
        CookieUtil.addCookieAttribute("username", username, 60*60, response);
        JedisUtil.set(username, password, 60 * 60);
        response.sendRedirect(redirect+"?username="+username);
    }
    
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.delCookieAttribute("username", request, response);
        CookieUtil.delCookieAttribute("client1-username", request, response);
        CookieUtil.delCookieAttribute("client2-username", request, response);
        return "forward:tologin";
        
    }
}
