package com.cdy.server.controller;

import com.cdy.CookieUtil;
import com.cdy.JedisUtil;
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
    public String user(HttpServletRequest request) {
        String token = request.getParameter("token");
        if (token == null || token.isEmpty()) {
            token = CookieUtil.getCookie(Contants.server_token, request);
        }
        String password = JedisUtil.get(token);
        return token + "-" + password;
    }
    
    @RequestMapping("/tologin")
    public String toLogin(HttpServletRequest request, HttpServletResponse response, String redirect, Model model) throws IOException {
        String username = CookieUtil.getCookie(Contants.server_token, request);
        if (username != null) {
            response.sendRedirect(redirect + "?token=" + username);
            return null;
        }
        model.addAttribute("redirect", redirect);
        return "login";
    }
    
    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response, String username, String password, String redirect) throws IOException {
        CookieUtil.addCookie(Contants.server_token, username, 60 * 60, response);
        JedisUtil.set(username, password, 60 * 60);
        response.sendRedirect(redirect + "?token=" + username);
    }
    
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, String token) {
        CookieUtil.delCookie(Contants.server_token, request, response);
        CookieUtil.delCookie(Contants.client1_token, request, response);
        CookieUtil.delCookie(Contants.client2_token, request, response);
        JedisUtil.delete(token);
        return "forward:tologin";
        
    }
}
