package com.tuitui.utils;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.interfaces.Claim;
import com.tuitui.component.Token;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //String token = request.getParameter("token");
        String token = request.getHeader("token");
        Map decode = JWTUtils.decode(token);
        ResultCode code = (ResultCode) decode.get("code");
        if(!code.equals(ResultCode.SUCCESS)) {
            // 直接返回
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSONUtil.toJsonStr(new Result().error(code)));
            return false;
        }
        Map<String, Claim> claims = (Map<String, Claim>) decode.get("claims");
        // 添加UID
        request.setAttribute("uid",claims.get("uid").asString());
        // 刷新token
        Token.updateExpirationTime(token);
        return true;
    }
}
