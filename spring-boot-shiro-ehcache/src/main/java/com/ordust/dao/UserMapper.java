package com.ordust.dao;

import com.ordust.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByUserName(String username);
}
