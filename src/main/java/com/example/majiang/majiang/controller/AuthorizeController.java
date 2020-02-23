package com.example.majiang.majiang.controller;

import com.example.majiang.majiang.dto.AccessTokenDTO;
import com.example.majiang.majiang.dto.GithubUser;
import com.example.majiang.majiang.mapper.UserMapper;
import com.example.majiang.majiang.model.User;
import com.example.majiang.majiang.provider.GithubProviter;
import com.example.majiang.majiang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProviter githubProviter;
//    这里value的意思是他会从我们的application.propersity文件中去读取github.client.id然后赋值到clientid中
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirecturi;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirecturi);
        String accessToken=githubProviter.getAccessToken(accessTokenDTO);
//        通过这里去获取到用户信息
        GithubUser githubUser=githubProviter.getUser(accessToken);
        if(githubUser!=null&&githubUser.getId()!=null){
            User user=new User();
            String token=UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatar_url());

            userService.createUpdate(user);

//            然后再把token写入到cookie中
            response.addCookie(new Cookie("token",token));

            return "redirect:/";
        }else {
//            登录失败 重新登录
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        删除session
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";

    }
}
