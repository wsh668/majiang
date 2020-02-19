package com.example.majiang.majiang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action, Model model){
        if(action.equals("questions")){
            model.addAttribute("section","questions");
            model.addAttribute("SectionName","我的提问");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("SectionName","最新回复");
        }
        return "profile";
    }
}
