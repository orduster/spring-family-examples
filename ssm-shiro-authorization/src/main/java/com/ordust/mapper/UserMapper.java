package com.ordust.mapper;

import com.ordust.domain.User;

public interface UserMapper {

    User getByUserName(String userName);
}