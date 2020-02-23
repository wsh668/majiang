package com.example.majiang.majiang.service;

import com.example.majiang.majiang.mapper.UserMapper;
import com.example.majiang.majiang.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createUpdate(User user) {
       User dbuser = userMapper.findByAccountId(user.getAccountId());
       if(dbuser == null){
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insert(user);
       }else {
           dbuser.setGmtModified(System.currentTimeMillis());
           dbuser.setAvatarUrl(user.getAvatarUrl());
           dbuser.setName(user.getName());
           dbuser.setToken(user.getToken());
           userMapper.update(dbuser);
       }


          }
}
