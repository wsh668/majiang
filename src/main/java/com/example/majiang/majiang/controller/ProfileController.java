package com.example.majiang.majiang.controller;

import com.example.majiang.majiang.dto.PaginationDTO;
import com.example.majiang.majiang.mapper.UserMapper;
import com.example.majiang.majiang.model.User;
import com.example.majiang.majiang.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action, Model model,
                          @RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size",defaultValue = "5")  Integer size,
                          HttpServletRequest request){
        User user =(User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        if(action.equals("questions")){
            model.addAttribute("section","questions");
            model.addAttribute("SectionName","我的提问");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("SectionName","最新回复");
        }
        PaginationDTO paginationDTO = questionService.list(user.getId(),page,size);
        model.addAttribute("pagination",paginationDTO);
        return "profile";
    }
}
