package com.orduster.controller;

import com.orduster.util.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(String username, String password, Model model) {
        //对密码进行加密
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            Session session = subject.getSession();
            session.setAttribute("userName", subject.getPrincipal().toString());
            return "redirect:index";
        } catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
            System.out.println(e.getMessage());
            model.addAttribute("error", "验证失败");
            return "login";
        } catch (AuthenticationException e) {
            model.addAttribute("error", "验证失败");
            System.out.println(e.getMessage());
            return "login";
        }
    }


    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping("/403")
    public String unauthorized() {
        return "403";
    }
}