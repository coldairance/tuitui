package com.tuitui.dao.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;

@Mapper
public interface BookMapper {
    @Insert("insert tt_sub(uid,bid) values (#{uid},#{bid})")
    void insertBook(@Param("uid") String uid, @Param("bid") String bid) throws SQLException;

    @Delete("delete from tt_sub where uid=#{uid}  and bid=#{bid}")
    Integer deleteBook(@Param("uid") String uid, @Param("bid") String bid);
}
