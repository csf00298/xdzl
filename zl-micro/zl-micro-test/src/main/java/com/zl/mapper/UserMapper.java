package com.zl.mapper;

import com.zl.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 */
@Mapper
public interface UserMapper {
    public User selectById(@Param("id") int id);

    public List<User> list();
}
