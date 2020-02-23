package com.example.majiang.majiang.mapper;

import com.example.majiang.majiang.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    public void insert(User user);

//    这里为什么要加@param  是因为这里不是一个user类  而是我们自己写的 String  如果是user类就不需要加@param
    @Select("select * from user where token=#{token}")
    User findbytoken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User finById(@Param("id") Integer creator);

    @Select("select * from user where id=1")
    User getu();

    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name},token = #{token},gmt_modified = #{gmtModified},avatar_url = #{avatarUrl} where id =#{id}")
    void update(User user);
}
