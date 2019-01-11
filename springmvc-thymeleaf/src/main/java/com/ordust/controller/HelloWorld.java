package com.ordust.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorld {

    @RequestMapping("/")
    public String helloworld(Model model) {
        model.addAttribute("helloworld", "helloworld");
        return "helloworld";
    }

}