package com.example.majiang.majiang.interceptor;

import com.example.majiang.majiang.mapper.UserMapper;
import com.example.majiang.majiang.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) {
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
                    break;
                }
            }
        }
        return true;
    }

    public void postHandle() {  }

    public void afterCompletion()  { }

}
