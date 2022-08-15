package com.tuitui.utils;

import cn.hutool.http.HttpRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tuitui.component.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
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
    public static Map decode(String token) {
        // 创建验证对象
        JWTVerifier build = JWT.require(Algorithm.HMAC256(SING)).build();

        DecodedJWT verify = null;
        Map<String, Object> ret = new HashMap<>();
        try{
            // 解码信息
            verify = build.verify(token);
        }catch (Exception e){
            ret.put("code",ResultCode.TOKEN_ERROR);
            return ret;
        }
        // 获取用户id
        String uid = verify.getClaim("uid").asString();
        String old = Token.get(uid);
        if(old == null) {
            // 账号过期
            ret.put("code",ResultCode.USER_ACCOUNT_EXPIRED);
            return ret;
        }
        if(!old.equals(token)) {
            // 账号在其他地方登录
            ret.put("code",ResultCode.USER_ACCOUNT_USE_BY_OTHERS);
            return ret;
        }
        Map<String, Claim> claims = verify.getClaims();
        ret.put("claims",claims);
        ret.put("code",ResultCode.SUCCESS);
        return ret;
    }
}
