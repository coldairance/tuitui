package com.tuitui.controller;
import com.tuitui.dao.mapper.BookMapper;
import com.tuitui.utils.Result;
import com.tuitui.utils.ResultCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@RestController
@RequestMapping("/verify")
public class UserController {

    @Autowired
    private BookMapper bookMapper;

    @GetMapping("/loging")
    @ApiOperation(value = "登录校验",notes = "判断用户是否登录")
    public Result register(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return new Result().ok();
    }

    @GetMapping("/book/add")
    @ApiOperation(value = "添加书籍",notes = "根据用户ID和书籍ID添加书籍")
    public Result addBook(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam String bid,
            @RequestAttribute String uid
    ) {
        Result result = new Result();
        try {
            bookMapper.insertBook(uid,bid);
        }catch (Exception e){
            result.error(ResultCode.DUB_INSERT);
            return result;
        }

        return result.ok();
    }

    @GetMapping("/book/del")
    @ApiOperation(value = "删除书籍",notes = "根据用户ID和书籍ID删除书籍")
    public Result delBook(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam String bid,
            @RequestAttribute String uid
    ) {
        Result result = new Result();
        Integer integer = bookMapper.deleteBook(uid, bid);
        if(integer==0){
            result.error(ResultCode.BOOK_NOT_EXIST);
        }else{
            result.ok();
        }
        return result;
    }
}
