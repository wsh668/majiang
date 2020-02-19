package com.example.majiang.majiang.controller;

import com.example.majiang.majiang.dto.PaginationDTO;
import com.example.majiang.majiang.dto.QuestionDTO;
import com.example.majiang.majiang.mapper.UserMapper;
import com.example.majiang.majiang.model.User;
import com.example.majiang.majiang.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "size",defaultValue = "2") Integer size){
        Cookie[] cookies =request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findbytoken(token);
                    //                让前端通过session去判断是否登录 获取登录信息
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                }
            }
        }
        PaginationDTO pagination =questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
