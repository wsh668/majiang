package com.example.majiang.majiang.controller;


import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {
    @RequestMapping("/test")
    public String test(@RequestParam(name="name",defaultValue = "world") String name,
    Model model){
      model.addAttribute("name",name);
      return "test";
    }
}
