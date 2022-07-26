package com.tuitui.test;

import com.tuitui.component.BCryptPasswordEncoder;
import com.tuitui.dao.entity.User;
import com.tuitui.dao.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    void testMySQL(){
        User user = new User();
        user.setPassword("2");
        userMapper.insertUser(user);
    }

    @Test
    void testPasswordEncoder(){

        String password = "123456";
        String encode = encoder.encode(password);
        System.out.println(encode);
        System.out.println(encoder.matches(password,encode));
    }

    @Test
    void testValidation(){
        User user = new User();
        user.setUsername("12");
    }
}
