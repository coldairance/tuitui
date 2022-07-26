package com.tuitui.dao.entity;
import com.tuitui.validation.LoginValidation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Digits;
@Data
@ApiModel(description = "用户")
public class User {

    @Digits(integer = 19,fraction = 0,message = LoginValidation.USER_ID,groups = {LoginValidation.Login.class})
    @ApiModelProperty(value = "用户ID："+LoginValidation.USER_ID)
    private Long uid;

    @Length(min = 5,max = 10, message = LoginValidation.PASSWORD,groups = {LoginValidation.Login.class,LoginValidation.Register.class})
    @ApiModelProperty(value = "密码："+LoginValidation.PASSWORD)
    private String password;

    @Length(min=3,max=8, message = LoginValidation.USER_NAME,groups = {LoginValidation.Register.class})
    @ApiModelProperty(value = "用户名："+LoginValidation.USER_NAME)
    private String username;

}
