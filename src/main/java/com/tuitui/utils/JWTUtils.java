package com.tuitui.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tuitui.component.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.security.auth.login.AccountExpiredException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JWTUtils {


    private static String SING;

    @Value("${jwt.key}")
    public void setSING(String SING) {
        JWTUtils.SING = SING;
    }


    /**
     * 生成token  header.payload.signature
     */
    public static String getToken(Map<String, String> map) {

        // 创建jwt builder
        JWTCreator.Builder builder = JWT.create();

        // payload
        map.forEach((k,v) -> {
            builder.withClaim(k, v);
        });

        // 签名
        String token = builder.sign(Algorithm.HMAC256(SING));

        return token;
    }


    /**
     * 验证 token 合法性
     */
    public static ResultCode check(String token) {
        // 创建验证对象
        JWTVerifier build = JWT.require(Algorithm.HMAC256(SING)).build();


        DecodedJWT verify = null;
        try{
            // 解码信息
            verify = build.verify(token);
        }catch (Exception e){
            return ResultCode.TOKEN_ERROR;
        }
        // 获取用户id
        String uid = verify.getClaim("uid").asString();

        String old = Token.get(uid);

        if(old == null) {
            // 账号过期
            return ResultCode.USER_ACCOUNT_EXPIRED;
        }

        if(!old.equals(token)) {
            // 账号在其他地方登录
            return ResultCode.USER_ACCOUNT_USE_BY_OTHERS;
        }

        return ResultCode.SUCCESS;
    }
}
