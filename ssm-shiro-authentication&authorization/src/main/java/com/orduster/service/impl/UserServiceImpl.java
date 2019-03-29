package com.orduster.service.impl;

import com.orduster.domain.User;
import com.orduster.mapper.UserMapper;
import com.orduster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String getPassword(String name) {
        User user = userMapper.getByUserName(name);
        if (null == user)
            return null;
        return user.getPassword();
    }
}
