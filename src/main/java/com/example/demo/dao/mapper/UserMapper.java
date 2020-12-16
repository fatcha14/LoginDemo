package com.example.demo.dao.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    @Select("SELECT USERNAME FROM USER WHERE USERNAME=#{username}")
    @Results({@Result(property = "username",column = "username")})
    User findExistingUser(@Param("username") String username);

    @Insert("INSERT INTO USER VALUES(#{id},#{username},#{password},#{customerName},#{customerEmail},#{customerDateOfBirth},#{customerAddress},0)")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void register(User user);

    @Select("SELECT * FROM USER WHERE USERNAME = #{username} AND PASSWORD = #{password}")
    User login(User user);

    @Update("UPDATE USER SET PASSWORD = #{newPassword} WHERE USERNAME = #{username}")
    void updatePassword(User user);

    @Update("UPDATE USER SET FAILEDATTEMPT = FAILEDATTEMPT + 1 WHERE USERNAME = #{username}")
    void incrementFailedAttempt(User user);

    @Select("SELECT FAILEDATTEMPT FROM USER WHERE USERNAME=#{username}")
    int getFailedAttempt(User user);
}
