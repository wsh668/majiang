package com.example.majiang.majiang.provider;

import com.alibaba.fastjson.JSON;
import com.example.majiang.majiang.dto.AccessTokenDTO;
import com.example.majiang.majiang.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProviter {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string= response.body().string();
            System.out.println(string);
////            access_token=67a03450d48e4f9c1c94318bd06a5e07f405909a&scope=user&token_type=bearer把这个结果进行拆分获取token
//            String[] tokenstr1=string.split("&");
////            通过&号拆分获取到access_token=67a03450d48e4f9c1c94318bd06a5e07f405909a
//            String tokenstr2=tokenstr1[0];
//            String[] tokenstr3=tokenstr2.split("=");
//            String token=tokenstr3[1];
            String token=string.split("&")[0].split("=")[1];
            return token;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string=response.body().string();
           GithubUser githubUser= JSON.parseObject(string,GithubUser.class);
           return githubUser;
        }catch (IOException e){}
      return null;
    }
}
