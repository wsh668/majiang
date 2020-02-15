package com.example.majiang.majiang.controller;

import com.example.majiang.majiang.dto.AccessTokenDTO;
import com.example.majiang.majiang.dto.GithubUser;
import com.example.majiang.majiang.provider.GithubProviter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirecturi);
        String accessToken=githubProviter.getAccessToken(accessTokenDTO);
        GithubUser user=githubProviter.getUser(accessToken);
        System.out.println(user.getName());
        return "index";

    }
}
