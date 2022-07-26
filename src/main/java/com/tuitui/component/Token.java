package com.tuitui.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;


@Component
public class Token {

    private static Integer expirationTime;

    private static StringRedisTemplate stringRedisTemplate;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        Token.stringRedisTemplate = stringRedisTemplate;
    }

    @Value("${jwt.expiration-time}")
    public void setExpirationTime(Integer expirationTime) {
        Token.expirationTime = expirationTime;
    }

    private Token() {

    }


    public static void set(String uid, String token) {
        stringRedisTemplate.opsForValue().set(uid+"-token", token, Duration.ofDays(expirationTime));
    }

    public static String get(String username) {
        return stringRedisTemplate.opsForValue().get(username+"-token");
    }

    public static void updateExpirationTime(String username) {
        stringRedisTemplate.expire(username + "-token", Duration.ofDays(expirationTime));
    }

    public static void remove(String username) {
        stringRedisTemplate.delete(username + "-token");
    }

}
