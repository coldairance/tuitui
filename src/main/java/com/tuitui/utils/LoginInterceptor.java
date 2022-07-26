package com.tuitui.utils;

import cn.hutool.json.JSONUtil;
import com.tuitui.component.Token;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getParameter("token");
        ResultCode check = JWTUtils.check(token);
        if(!check.equals(ResultCode.SUCCESS)){
            // 直接返回
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSONUtil.toJsonStr(new Result().error(check)));
            return false;
        }

        // 刷新token
        Token.updateExpirationTime(token);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
