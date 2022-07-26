package com.tuitui.dao.mapper;
import com.tuitui.dao.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert tt_user(uid ,username, password) values (#{uid},#{username},#{password})")
    void insertUser(User user);
    @Select("select * from tt_user where uid=#{uid}")
    User selectUser(Long uid);
}
