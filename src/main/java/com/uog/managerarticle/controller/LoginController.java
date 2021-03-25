package com.uog.managerarticle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class LoginController {

    @GetMapping("/access-denied")
    public String AccessDenied(){
        return "authen/access-denied";
    }

    @GetMapping("/login")
    public String login(){
        return "authen/login";
    }
}
