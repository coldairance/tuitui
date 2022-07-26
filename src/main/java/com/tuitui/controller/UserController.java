package com.tuitui.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.tuitui.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/verify")
public class UserController {

    @GetMapping("/test")
    public Result register(
            HttpServerRequest request,
            HttpServletResponse response
    ) {
        System.out.println("enter...");
        return null;
    }
}
