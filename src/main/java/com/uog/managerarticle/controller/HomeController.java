package com.uog.managerarticle.controller;

import com.uog.managerarticle.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private IArticleService articleService;

    @RequestMapping("/")
    public String HomePage(Model model){
//        model.addAttribute("articles",articleService.findAllByStatus(1));
        return "home";
    }

    @RequestMapping("/home")
    public String home(){
        return "home";
    }

}
