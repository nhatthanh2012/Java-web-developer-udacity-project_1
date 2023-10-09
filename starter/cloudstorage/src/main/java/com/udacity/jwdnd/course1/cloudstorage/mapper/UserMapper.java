package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entities.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // query user exist
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    Users getUser(String username);

    // insert (userId, username, salt, password, firstName, lastName) new user
    @Insert("INSERT INTO USERS (username , salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")

    // for auto increment userid
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(Users users);
}
