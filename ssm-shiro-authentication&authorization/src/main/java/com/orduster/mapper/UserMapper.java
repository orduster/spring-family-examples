package com.orduster.mapper;

import com.orduster.domain.User;

public interface UserMapper {

    //根据用户名查询用户信息
    User getByUserName(String userName);
}
