package com.ordust.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloWorld {

    @RequestMapping("/")
    public String helloWorld() {
        return "helloworld";
    }

}