package com.example.majiang.majiang.controller;


import com.example.majiang.majiang.mapper.UserMapper;
import com.example.majiang.majiang.model.User;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    UserMapper userMapper;
    @RequestMapping("/test")
    public String test(@RequestParam(name="name",defaultValue = "world") String name,
    Model model){
      model.addAttribute("name",name);
      return "test";
    }


    @RequestMapping("/a")
    @ResponseBody
    public User user(){
        return userMapper.getu();
    }
}
