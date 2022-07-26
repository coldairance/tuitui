package com.tuitui.controller;

import com.tuitui.component.BCryptPasswordEncoder;
import com.tuitui.component.SnowFlake;
import com.tuitui.component.Token;
import com.tuitui.dao.entity.User;
import com.tuitui.dao.mapper.UserMapper;
import com.tuitui.utils.JWTUtils;
import com.tuitui.utils.Result;
import com.tuitui.utils.ResultCode;
import com.tuitui.validation.LoginValidation;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@Validated
public class LoginController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SnowFlake snowflake;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册",notes = "返回用户ID")
    public Result register(
            @RequestBody @Validated(LoginValidation.Register.class) User user
    ) {

        long uid = snowflake.nextId();
        user.setUid(uid);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insertUser(user);
        Result result = new Result();
        result.ok().add("uid",uid);

        return result;
    }


    @PostMapping("/login")
    @ApiOperation(value = "用户登录",notes = "返回用户token")
    public Result login(
            @RequestBody @Validated(LoginValidation.Login.class) User user
    ) {
        User old = userMapper.selectUser(user.getUid());
        Result result = new Result();
        if(old==null){
            result.error(ResultCode.USER_ACCOUNT_NOT_EXIST);
        }else if(!passwordEncoder.matches(user.getPassword(),old.getPassword())){
            result.error(ResultCode.USER_CREDENTIALS_ERROR);
        }else{
            result.ok();
            Map<String,String> map = new HashMap<>();
            map.put("uid",user.getUid().toString());
            map.put("username",user.getUsername());
            map.put("time", String.valueOf(System.currentTimeMillis()));
            String token = JWTUtils.getToken(map);
            result.add("token",token);

            // 放入缓存
            Token.set(user.getUid().toString(),token);
        }
        return result;
    }
}
