package com.orduster.service.impl;

import com.orduster.domain.User;
import com.orduster.domain.UserExample;
import com.orduster.mapper.UserMapper;
import com.orduster.service.UserRoleService;
import com.orduster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public String getPassword(String name) {
        User user = getByName(name);
        if (null == user)
            return null;
        return user.getPassword();
    }

    @Override
    public User getByName(String name) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.isEmpty())
            return null;
        return users.get(0);
    }

    @Override
    public List<User> list() {
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("id desc");
        return userMapper.selectByExample(userExample);
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
        userRoleService.deleteByUser(id);
    }

    @Override
    public User get(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }
}
