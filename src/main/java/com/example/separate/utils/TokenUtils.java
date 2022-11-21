package com.example.separate.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.separate.entity.User;
import com.example.separate.service.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
@Component
public class TokenUtils {

    private static IUserService staticUserService;

    @Resource
    private IUserService userService;

    @PostConstruct
    public void setUserService(){
        staticUserService = userService;
    }

    public static String getToken(String userId,String sign){
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面
                .withExpiresAt(DateUtil.offsetHour(new Date(),24)) //2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

    public static User getCurrentUser(){
        try {
            HttpServletRequest servletRequest = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            String token = servletRequest.getHeader("token");
            if (StrUtil.isNotBlank(token)){
                String userId = JWT.decode(token).getAudience().get(0);
                User user = staticUserService.getById(Integer.valueOf(userId));
                return user;
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }


}
